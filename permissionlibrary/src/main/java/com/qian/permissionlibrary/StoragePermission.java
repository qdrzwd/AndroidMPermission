package com.qian.permissionlibrary;

import android.Manifest;

import com.qian.permissionlibrary.util.PermissionUtil;

/**
 * Created by qiandong on 16/4/19.
 */
public class StoragePermission extends PermissionImpl {

    public StoragePermission(GrantedPermissionCallback grantedPermissionCallback){
        super(grantedPermissionCallback);
    }

    @Override
    public int getRequestCode() {
        return PermissionUtil.REQUEST_CODE_PERMISSION_STORAGE;
    }

    @Override
    public String getRequestPermission() {
        return Manifest.permission.READ_EXTERNAL_STORAGE;
    }

    @Override
    public String getRationalFlag() {
        return PermissionUtil.STORAGE_PERMISSION_RATIONLA;
    }

    @Override
    public boolean isForcePermission() {
        return false;
    }

    @Override
    public String getRationalString() {
        return activity.getString(R.string.permission_storage_message);
    }
}
