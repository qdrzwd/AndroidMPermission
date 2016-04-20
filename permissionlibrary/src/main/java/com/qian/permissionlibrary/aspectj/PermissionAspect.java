package com.qian.permissionlibrary.aspectj;

import com.qian.permissionlibrary.CameraPermission;
import com.qian.permissionlibrary.LocationPermission;
import com.qian.permissionlibrary.PermissionImpl;
import com.qian.permissionlibrary.PermissionInterface;
import com.qian.permissionlibrary.PhoneStatePermission;
import com.qian.permissionlibrary.StoragePermission;
import com.qian.permissionlibrary.VoicePermission;
import com.qian.permissionlibrary.ui.BaseActivity;
import com.qian.permissionlibrary.ui.BaseFragment;
import com.qian.permissionlibrary.util.PermissionUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by qiandong on 16/4/19.
 */
@Aspect
public class PermissionAspect {

    @Around("execution(@PermissionGrantAnnotation void com.qian.androidmpermission..*.*(..)) && @annotation(ann)")
    public void check(final ProceedingJoinPoint joinPoint,PermissionGrantAnnotation ann){
        Object obj = joinPoint.getThis();
        if(obj instanceof BaseActivity || obj instanceof BaseFragment){
            int neededPermission = ann.grantPermissionFlag();
            PermissionInterface permissionInterface = null;
            PermissionImpl.GrantedPermissionCallback callback = new PermissionImpl.GrantedPermissionCallback() {
                @Override
                public void grantedPermission() {
                    try {
                        joinPoint.proceed();
                    }catch (Throwable t){

                    }
                }
            };
            switch (neededPermission){
                case PermissionUtil.REQUEST_CODE_PERMISSION_STORAGE:
                    permissionInterface = new StoragePermission(callback);
                    break;
                case PermissionUtil.REQUEST_CODE_PERMISSION_CAMERA:
                    permissionInterface = new CameraPermission(callback);
                    break;
                case PermissionUtil.REQUEST_CODE_PERMISSION_LOCATION:
                    permissionInterface = new LocationPermission(callback);
                    break;
                case PermissionUtil.REQUEST_CODE_PERMISSION_READ_PHONE:
                    permissionInterface = new PhoneStatePermission(callback);
                    break;
                case PermissionUtil.REQUEST_CODE_PERMISSION_VOICE:
                    permissionInterface = new VoicePermission(callback);
                    break;
            }

            if(obj instanceof BaseActivity){
                BaseActivity baseActivity = (BaseActivity)obj;
                if(permissionInterface != null){
                    permissionInterface.checkPermission(baseActivity);
                }
            }else{
                BaseFragment baseFragment = (BaseFragment)obj;
                if(permissionInterface != null){
                    permissionInterface.checkPermissionFromFragment(baseFragment);
                }
            }
        }

        return;
    }
}
