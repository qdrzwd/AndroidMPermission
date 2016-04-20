package com.qian.permissionlibrary;

import android.Manifest;

import com.qian.permissionlibrary.util.PermissionUtil;

/**
 * Created by qiandong on 16/4/19.
 */
public class PhoneStatePermission extends PermissionImpl {

    public PhoneStatePermission(GrantedPermissionCallback grantedPermissionCallback){
        super(grantedPermissionCallback);
    }

    @Override
    public int getRequestCode() {
        return PermissionUtil.REQUEST_CODE_PERMISSION_READ_PHONE;
    }

    @Override
    public String getRequestPermission() {
        return Manifest.permission.READ_PHONE_STATE;
    }

    @Override
    public String getRationalFlag() {
        return PermissionUtil.PHONE_PERMISSION_RATIONLA;
    }

    @Override
    public String getRationalString() {
        return activity.getString(R.string.permission_phone_state_message);
    }
}
