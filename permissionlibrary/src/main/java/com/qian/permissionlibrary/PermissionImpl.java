package com.qian.permissionlibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.qian.permissionlibrary.ui.BaseActivity;
import com.qian.permissionlibrary.ui.BaseFragment;
import com.qian.permissionlibrary.util.PermissionUtil;

/**
 * Created by qiandong on 16/4/19.
 * */
public abstract class PermissionImpl implements PermissionInterface {

    private boolean mHasPermission = false;
    private boolean mFromFragment = false;
    protected Activity activity;
    protected Fragment fragment;
    protected GrantedPermissionCallback mGrantedPermissionCallback;
    protected GrantedFailCallback mGrantedFailCallback;

    public PermissionImpl(GrantedPermissionCallback grantedPermissionCallback){
        mGrantedPermissionCallback = grantedPermissionCallback;
    }

    public abstract String getRationalFlag();

    public abstract String getRationalString();

    public boolean isForcePermission(){
        return true;
    }

    public static int getMaskRequestCode(int requestCode,boolean fromFragment){
        if ((requestCode & PermissionUtil.REQUEST_CODE_PERMISSION_MASK) != 0) {
            throw new IllegalArgumentException("Can only use lower 4 bits for requestCode");
        }
        if(fromFragment){
            return requestCode | PermissionUtil.REQUEST_CODE_PERMISSION_MASK;
        }else{
            return requestCode | PermissionUtil.REQUEST_CODE_PERMISSION_ACTIVYTY_MASK;
        }
    }

    public static int getRealRequestCode(int requestCode){
        if(requestCode > PermissionUtil.REQUEST_CODE_PERMISSION_ACTIVYTY_MASK){
            return requestCode & (~PermissionUtil.REQUEST_CODE_PERMISSION_ACTIVYTY_MASK);
        }else{
            return requestCode & (~PermissionUtil.REQUEST_CODE_PERMISSION_MASK);
        }
    }

    @Override
    public void checkPermission(Activity activity) {
        this.activity = activity;
        initPermission();

        mHasPermission = PermissionUtil.checkSelfPermission(activity, getRequestPermission());
        if(!mHasPermission){
            denyPermission();
        }else{
            grantedPermission();
        }
    }

    private void initPermission(){
        if(mFromFragment && fragment instanceof BaseFragment){
            BaseFragment baseFragment = (BaseFragment)fragment;
            baseFragment.setPermissionInterface(this);
        }else if(activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.setPermissionInterface(this);
        }
    }

    @Override
    public void checkPermissionFromFragment(Fragment fragment) {
        if(fragment.getActivity() == null){
            return;
        }
        mFromFragment = true;
        this.fragment = fragment;
        checkPermission(fragment.getActivity());
    }

    @Override
    public boolean isgrantedPermission() {
        return mHasPermission;
    }

    public void grantedPermission() {
        mHasPermission = true;
        if(mGrantedPermissionCallback != null){
            mGrantedPermissionCallback.grantedPermission();
        }
    }

    @Override
    public void denyPermission() {
        if(getStateRational()){
            popPermissionDialog();
        }else{
            boolean phoneStateRational = PermissionUtil.shouldPhoneStateRational(activity, getRequestPermission());
            PermissionUtil.storeShouldPhoneStateRational(activity, getRationalFlag(), phoneStateRational);
            PermissionUtil.requestPermission(activity, getRequestPermission(), getMaskRequestCode(getRequestCode(), mFromFragment));
        }
    }

    public boolean getStateRational(){
        //用于判断never ask again
        boolean mStateShouldShowRational = PermissionUtil.getShouldPhoneStateRational(activity, getRationalFlag());
        boolean phoneStateRational = PermissionUtil.shouldPhoneStateRational(activity, getRequestPermission());
        if(mStateShouldShowRational && !phoneStateRational){
            return true;
        }

        return false;
    }

    private void forcePermission(){
        denyPermission();
    }

    private void popPermissionDialog(){
        PermissionUtil.popPermissionDialog(activity, getMaskRequestCode(getRequestCode(), mFromFragment)
                , isForcePermission(), getRationalString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(getRequestCode() == getRealRequestCode(requestCode)){
            if (PermissionUtil.verifyPermissions(grantResults)) {
                mHasPermission = true;
                grantedPermission();
            }else{
                if(isForcePermission()){
                    forcePermission();
                }
                if(mGrantedFailCallback != null){
                    mGrantedFailCallback.grantedFail();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(getRequestCode() == getRealRequestCode(requestCode)){
            if (PermissionUtil.checkSelfPermission(activity, getRequestPermission())) {
                mHasPermission = true;
                grantedPermission();
            } else {
                if(isForcePermission()){
                    popPermissionDialog();
                }
                if(mGrantedFailCallback != null){
                    mGrantedFailCallback.grantedFail();
                }
            }
        }
    }

    public void setGrantedFailCallback(GrantedFailCallback grantedFailCallback) {
        this.mGrantedFailCallback = grantedFailCallback;
    }

    public interface GrantedPermissionCallback{
        public abstract void grantedPermission();
    }

    public interface GrantedFailCallback{
        public abstract void grantedFail();
    }
}
