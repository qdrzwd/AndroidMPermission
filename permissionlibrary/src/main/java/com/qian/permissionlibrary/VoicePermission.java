package com.qian.permissionlibrary;

import android.Manifest;

import com.qian.permissionlibrary.util.PermissionUtil;

/**
 * Created by qiandong on 16/4/19.
 */
public class VoicePermission extends PermissionImpl {

    public VoicePermission(GrantedPermissionCallback grantedPermissionCallback){
        super(grantedPermissionCallback);
    }

    @Override
    public int getRequestCode() {
        return PermissionUtil.REQUEST_CODE_PERMISSION_VOICE;
    }

    @Override
    public String getRequestPermission() {
        return Manifest.permission.RECORD_AUDIO;
    }

    @Override
    public String getRationalFlag() {
        return PermissionUtil.VOICE_PERMISSION_RATIONLA;
    }

    @Override
    public boolean isForcePermission() {
        return false;
    }

    @Override
    public String getRationalString() {
        return activity.getString(R.string.permission_voice_message);
    }
}
