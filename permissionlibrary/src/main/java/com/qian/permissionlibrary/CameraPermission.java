package com.qian.permissionlibrary;

import android.Manifest;

import com.qian.permissionlibrary.util.PermissionUtil;

/**
 * Created by qiandong on 16/4/19.
 */
public class CameraPermission extends PermissionImpl {

    public CameraPermission(GrantedPermissionCallback grantedPermissionCallback){
        super(grantedPermissionCallback);
    }

    @Override
    public int getRequestCode() {
        return PermissionUtil.REQUEST_CODE_PERMISSION_CAMERA;
    }

    @Override
    public String getRequestPermission() {
        return Manifest.permission.CAMERA;
    }

    @Override
    public String getRationalFlag() {
        return PermissionUtil.CAMERA_PERMISSION_RATIONLA;
    }

    @Override
    public boolean isForcePermission() {
        return false;
    }

    @Override
    public String getRationalString() {
        return activity.getString(R.string.permission_camera_message);
    }
}
