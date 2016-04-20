package com.qian.permissionlibrary;

import android.content.Intent;

/**
 * Created by qiandong on 16/4/19.
 */
public class PermissionEvent {
    public int requestCode;
    public String[] permissions;
    public int[] grantResults;

    public int resultCode;
    public Intent data;

    public boolean isSettingRequest;

    public PermissionEvent(int requestCode, String[] permissions, int[] grantResults){
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        isSettingRequest = false;
    }

    public PermissionEvent(int requestCode, int resultCode, Intent data){
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
        isSettingRequest = true;
    }
}
