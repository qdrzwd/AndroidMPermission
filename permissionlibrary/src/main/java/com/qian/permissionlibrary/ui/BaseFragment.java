package com.qian.permissionlibrary.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qian.permissionlibrary.PermissionEvent;
import com.qian.permissionlibrary.PermissionImpl;
import com.qian.permissionlibrary.PermissionInterface;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by qiandong on 16/4/19.
 */
public class BaseFragment extends Fragment{

    private List<PermissionInterface> mPermissionInterfaceList;

    public void setPermissionInterface(PermissionInterface p) {
        if (mPermissionInterfaceList == null) {
            mPermissionInterfaceList = new ArrayList<PermissionInterface>();
        } else {
            for (PermissionInterface permissionInterface : mPermissionInterfaceList) {
                if (permissionInterface.getRequestPermission().equals(p.getRequestPermission())) {
                    mPermissionInterfaceList.remove(permissionInterface);
                    mPermissionInterfaceList.add(p);
                    return;
                }
            }
        }

        mPermissionInterfaceList.add(p);
    }

    public void onEventMainThread(PermissionEvent event) {
        if (mPermissionInterfaceList != null) {
            for (PermissionInterface permissionInterface : mPermissionInterfaceList) {
                if (event.isSettingRequest) {
                    permissionInterface.onActivityResult(event.requestCode, event.resultCode, event.data);
                } else {
                    if (permissionInterface.getRequestCode() == PermissionImpl.getRealRequestCode(event.requestCode)) {
                        permissionInterface.onRequestPermissionsResult(event.requestCode, event.permissions, event.grantResults);
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
