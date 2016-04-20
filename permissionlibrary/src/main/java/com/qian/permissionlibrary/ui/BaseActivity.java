package com.qian.permissionlibrary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.qian.permissionlibrary.PermissionEvent;
import com.qian.permissionlibrary.PermissionImpl;
import com.qian.permissionlibrary.PermissionInterface;
import com.qian.permissionlibrary.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by qiandong on 16/4/19.
 */
public class BaseActivity extends AppCompatActivity {

    private List<PermissionInterface> mPermissionInterfaceList;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void setPermissionInterface(PermissionInterface p){
        if(mPermissionInterfaceList == null){
            mPermissionInterfaceList = new ArrayList<PermissionInterface>();
        }else{
            for(PermissionInterface permissionInterface:mPermissionInterfaceList){
                if(permissionInterface.getRequestPermission().equals(p.getRequestPermission())){
                    mPermissionInterfaceList.remove(permissionInterface);
                    mPermissionInterfaceList.add(permissionInterface);
                    return;
                }
            }
        }

        mPermissionInterfaceList.add(p);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(PermissionUtil.isSettingRequest(requestCode)){
            if(requestCode < PermissionUtil.REQUEST_CODE_PERMISSION_ACTIVYTY_MASK){
                EventBus.getDefault().post(new PermissionEvent(requestCode, resultCode, data));
            }else if(mPermissionInterfaceList != null){
                for(PermissionInterface permissionInterface:mPermissionInterfaceList){
                    if(permissionInterface.getRequestCode() == PermissionImpl.getRealRequestCode(requestCode)){
                        permissionInterface.onActivityResult(requestCode, resultCode, data);
                    }
                }

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode < PermissionUtil.REQUEST_CODE_PERMISSION_ACTIVYTY_MASK) {
            EventBus.getDefault().post(new PermissionEvent(requestCode, permissions, grantResults));
        }else if (mPermissionInterfaceList != null){
            for(PermissionInterface permissionInterface:mPermissionInterfaceList){
                if(permissionInterface.getRequestCode() == PermissionImpl.getRealRequestCode(requestCode)){
                    permissionInterface.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    return;
                }
            }
        }

    }
}
