package com.temporary.demoproject.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.temporary.demoproject.R;
import com.temporary.presenter.WifiSwitcherPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WifiSwitcherActivity extends AppCompatActivity{
    @BindView(R.id.open_button)
    public Button mOpenBtn;
    @BindView(R.id.close_button)
    public Button mCloseBtn;
    private WifiSwitcherPresenter mWifiSwitcherPresenter;
    private WifiManager mWifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_switcher);
        ButterKnife.bind(this);
        checkPermission();

        init();
    }

    private void checkPermission() {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CHANGE_WIFI_STATE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_WIFI_STATE},
                    0);
        }*/
    }

    private void init() {
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mWifiSwitcherPresenter = new WifiSwitcherPresenter(this);
    }

    @OnClick({R.id.open_button, R.id.close_button})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.open_button:{
                mWifiSwitcherPresenter.openWifi(mWifiManager);
                break;
            }
            case R.id.close_button:{
                mWifiSwitcherPresenter.closeWifi(mWifiManager);
                break;
            }
        }
    }
}
