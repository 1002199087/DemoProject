package com.temporary.model;

import android.net.wifi.WifiManager;

/**
 * Created by Dee on 2018/5/31.
 */

public interface IWifiSwitcherModel {
    void openWifi(WifiManager manager);
    void closeWifi(WifiManager manager);
}
