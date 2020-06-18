package com.temporary.demoproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.temporary.demoproject.qmuidemo.QmuiMainActivity;

import io.reactivex.functions.Consumer;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.CHANGE_WIFI_STATE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_SETTINGS,
                android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};

        new RxPermissions(this).request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Intent intent = new Intent(SplashActivity.this, QmuiMainActivity.class);
                //Intent intent = new Intent(SplashActivity.this, LogAddActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
