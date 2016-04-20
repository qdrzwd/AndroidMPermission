package com.qian.permissionlibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by qiandong on 16/4/19.
 */
public interface PermissionInterface {

    public abstract void checkPermission(Activity activity);

    public abstract void checkPermissionFromFragment(Fragment fragment);

    public abstract boolean isgrantedPermission();

    public abstract void denyPermission();

    public abstract String getRequestPermission();

    public abstract int getRequestCode();

    public abstract void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
