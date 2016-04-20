package com.qian.androidmpermission;

import android.os.Bundle;
import android.os.Environment;

import com.qian.permissionlibrary.aspectj.PermissionGrantAnnotation;
import com.qian.permissionlibrary.ui.BaseActivity;
import com.qian.permissionlibrary.util.PermissionUtil;

import java.io.File;
import java.io.IOException;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
