package com.qian.permissionlibrary;

import android.Manifest;

import com.qian.permissionlibrary.util.PermissionUtil;

/**
 * Created by qiandong on 16/4/19.
 */
public class LocationPermission extends PermissionImpl {

    public LocationPermission(GrantedPermissionCallback grantedPermissionCallback){
        super(grantedPermissionCallback);
    }

    @Override
    public int getRequestCode() {
        return PermissionUtil.REQUEST_CODE_PERMISSION_LOCATION;
    }

    @Override
    public String getRequestPermission() {
        return Manifest.permission.ACCESS_FINE_LOCATION;
    }

    @Override
    public String getRationalFlag() {
        return PermissionUtil.LOCATION_PERMISSION_RATIONLA;
    }

    @Override
    public boolean isForcePermission() {
        return false;
    }

    @Override
    public String getRationalString() {
        return activity.getString(R.string.permission_location_message);
    }
}
