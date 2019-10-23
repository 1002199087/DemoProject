package com.temporary.presenter;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.temporary.model.WifiSwitcherModelImp;

/**
 * Created by Dee on 2018/5/31.
 */

public class WifiSwitcherPresenter {
    private WifiSwitcherModelImp mWifiSwitcherModelImp;
    private Context mContext;

    public WifiSwitcherPresenter(Context context) {
        this.mContext = context;
        this.mWifiSwitcherModelImp = new WifiSwitcherModelImp();
    }

    public void openWifi(WifiManager manager) {
        mWifiSwitcherModelImp.openWifi(manager);
    }

    public void closeWifi(WifiManager manager) {
        mWifiSwitcherModelImp.closeWifi(manager);
    }

}
