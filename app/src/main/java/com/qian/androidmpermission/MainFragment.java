package com.qian.androidmpermission;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qian.permissionlibrary.aspectj.PermissionGrantAnnotation;
import com.qian.permissionlibrary.ui.BaseFragment;
import com.qian.permissionlibrary.util.PermissionUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by qiandong on 16/4/19.
 */
public class MainFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkPermission();
    }

    @PermissionGrantAnnotation(grantPermissionFlag = PermissionUtil.REQUEST_CODE_PERMISSION_STORAGE)
    private void checkPermission(){
        File file = new File(Environment.getExternalStorageDirectory()+"/test");
        if(file.exists()){
            file.delete();
        }

        try {
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
