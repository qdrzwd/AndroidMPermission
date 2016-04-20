package com.qian.androidmpermission;

import android.os.Bundle;
import android.os.Environment;

import com.qian.permissionlibrary.aspectj.PermissionGrantAnnotation;
import com.qian.permissionlibrary.ui.BaseActivity;
import com.qian.permissionlibrary.util.PermissionUtil;

import java.io.File;
import java.io.IOException;

public class MainActivity2 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.empty,mainFragment).commit();
    }
}
