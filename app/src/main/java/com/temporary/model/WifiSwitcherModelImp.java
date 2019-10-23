package com.temporary.model;

import android.net.wifi.WifiManager;

/**
 * Created by Dee on 2018/5/31.
 */

public class WifiSwitcherModelImp implements IWifiSwitcherModel {
    @Override
    public void openWifi(WifiManager manager) {
        manager.setWifiEnabled(true);
    }

    @Override
    public void closeWifi(WifiManager manager) {
        manager.setWifiEnabled(false);
    }
}
