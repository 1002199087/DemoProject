package com.temporary.model;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.temporary.adapter.RecyclerViewAdapter;

import java.util.List;

/**
 * Created by Dee on 2018/5/21.
 */

public interface IRecyclerModel {
    RecyclerViewAdapter getScanResultAdapter(List<ScanResult> list, Context context);
    void reconnectWifiAp(WifiManager wifiManager);
    void uploadFileInFlashair(String name);
}
