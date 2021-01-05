package com.gxinyu.testsliverpermission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.gxinyu.sliverpermission.Permission;
import com.gxinyu.sliverpermission.RequestBuilder;
import com.gxinyu.sliverpermission.RequestCallBack;
import com.gxinyu.sliverpermission.SliverPermission;

public class MainActivity extends AppCompatActivity implements RequestCallBack {

    public static String GROUP_SOMES[] = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void camera(View view) {
        SliverPermission.with(this)
                .permissions(new String[]{Permission.CAMERA})
                .apply();
    }

    public void location(View view) {
        SliverPermission.with(this)
                .mode(RequestBuilder.CHAIN)
                .permissions(Permission.GROUP_LOCATION)
                .callback(this)
                .apply();
    }

    public void someGroup(View view) {
        SliverPermission.with(this)
                .permissions(GROUP_SOMES)
                .callback(this)
                .apply();
    }

    public void someGroup2(View view) {
        SliverPermission.with(this)
                .mode(RequestBuilder.CHAIN)
                .permissions(GROUP_SOMES)
                .callback(this)
                .apply();
    }

    //fragment申请权限
    public void appfragment(View view) {
        startActivity(new Intent(this, SupportActivity.class));
    }

    public void openSystem(View view) {
        Intent intent;
        try {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
        } catch (Exception e) {
            intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult:"+requestCode);
    }

    @Override
    public void onPermissionGranted(String[] permissionList) {
        Log.e("TAG", "申请权限被允许");
    }

    @Override
    public void onPermissionDenied(String permissionName, String message) {
        Log.e("TAG", "申请权限被拒绝:" + permissionName);
    }

    @Override
    public void onPermissionDeniedNotAllowed(String permissionName) {
        Log.e("TAG", "申请权限不再被申请:" + permissionName);
    }

}