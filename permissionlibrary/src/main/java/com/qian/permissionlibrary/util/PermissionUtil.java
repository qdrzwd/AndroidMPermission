package com.qian.permissionlibrary.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.qian.permissionlibrary.PermissionImpl;
import com.qian.permissionlibrary.R;

/**
 * Created by qiandong on 16/4/19.
 */
public class PermissionUtil {

    public static final int REQUEST_CODE_PERMISSION_READ_PHONE = 0x01;
    public static final int REQUEST_CODE_PERMISSION_LOCATION = 0x02;
    public static final int REQUEST_CODE_PERMISSION_STORAGE = 0x03;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 0x04;
    public static final int REQUEST_CODE_PERMISSION_VOICE = 0x05;

    public static final int REQUEST_CODE_PERMISSION_MASK = 0x80;
    public static final int REQUEST_CODE_PERMISSION_ACTIVYTY_MASK = 0xf0;

    public static final String PHONE_PERMISSION_RATIONLA = "phone_permission_rational";
    public static final String LOCATION_PERMISSION_RATIONLA = "location_permission_rational";
    public static final String STORAGE_PERMISSION_RATIONLA = "storage_permission_rational";
    public static final String CAMERA_PERMISSION_RATIONLA = "camera_permission_rational";
    public static final String VOICE_PERMISSION_RATIONLA = "voice_permission_rational";

    public static final String PERMISSION_SP_FLAG = "permission_sp_flag";

    public static boolean checkSelfPermission(Context context, String permission) {
        if (null == context) {
            return false;
        }

        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
            return false;
        }

        return true;
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        if (null == activity) {
            return false;
        }

        if(!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            return false;
        }

        return true;
    }

    public static boolean verifyPermissions(int[] grantResults) {
        if (null == grantResults || grantResults.length < 1) {
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTelephonyManagerPermission(Context context) {
        return checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
    }

    public static void requestPermission(Activity activity,String permission,int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public static void requestPermission(Activity activity,String[] permissions,int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void showPermissionDialog(final Activity activity, final int requestCode
            , final boolean force, String msg) {
        if (activity == null || activity.isFinishing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(msg);

        builder.setNegativeButton(R.string.permission_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (force && !activity.isFinishing()) {
                    activity.finish();
                }
            }
        });

        builder.setPositiveButton(R.string.permission_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, requestCode);
            }
        });

        builder.create().show();
    }

    public static boolean getShouldRequestPermissionRationale(Context context, String flag){
        if (null == context) {
            return false;
        }
        SharedPreferences statusSharedPreferences = context.getSharedPreferences(PERMISSION_SP_FLAG, 0);
        return statusSharedPreferences.getBoolean(flag, false);
    }

    public static void storeShouldRequestPermissionRationale(Context context, String flag, boolean rational){
        if (null == context) {
            return;
        }
        SharedPreferences statusSharedPreferences = context.getSharedPreferences(PERMISSION_SP_FLAG, 0);
        SharedPreferences.Editor editor = statusSharedPreferences.edit();
        editor.putBoolean(flag, rational);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static boolean isSettingRequest(int requestCode){
        int code = PermissionImpl.getRealRequestCode(requestCode);
        return code == REQUEST_CODE_PERMISSION_READ_PHONE
                || code == REQUEST_CODE_PERMISSION_LOCATION
                || code == REQUEST_CODE_PERMISSION_STORAGE
                || code == REQUEST_CODE_PERMISSION_CAMERA
                || code == REQUEST_CODE_PERMISSION_VOICE;
    }
}
