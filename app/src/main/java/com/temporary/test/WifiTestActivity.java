package com.temporary.test;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.temporary.demoproject.R;

import java.util.List;

public class WifiTestActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mEconapBtn;
    private Button mFlashairBtn;
    private WifiManager mWifiManager;
    private static final String WIFI_TYPE_NOPASS = "nopass";
    private static final String WIFI_TYPE_WEP = "wep";
    private static final String WIFI_TYPE_WPA = "wpa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_test);

        requestPermissions();
        init();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_WIFI_STATE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CHANGE_WIFI_STATE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }

    private void init() {
        mEconapBtn = (Button) findViewById(R.id.econ_ap_button);
        mFlashairBtn = (Button) findViewById(R.id.flashair_button);

        mEconapBtn.setOnClickListener(this);
        mFlashairBtn.setOnClickListener(this);

        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.econ_ap_button: {
                List<ScanResult> list = mWifiManager.getScanResults();
                for (ScanResult result : list) {
                    if ("econ-ap".equals(result.SSID)) {
                        Log.e("wyy", "WifiTestActivity onClick result aaa = "+result.SSID);
                        connectWifiAp(result, "econweb1");
                    }
                }

                break;
            }
            case R.id.flashair_button: {
                List<ScanResult> list = mWifiManager.getScanResults();
                for (ScanResult result : list) {
                    if ("wyy".equals(result.SSID)) {
                        Log.e("wyy", "WifiTestActivity onClick result bbb = "+result.SSID);
                        connectWifiAp(result, "12345678");
                    }
                }
                break;
            }
        }
    }

    public void connectWifiAp(ScanResult result, String password) {/*连接指定热点*/
        mWifiManager.disconnect();
        WifiConfiguration config = createWifiConfiguration(result, password,
                WIFI_TYPE_WPA);//WIFI_TYPE_WPA econweb1 12345678
        int id = mWifiManager.addNetwork(config);
        Log.e("wyy", "WifiTestActivity connectWifiAp id = " + id);
        boolean enable = mWifiManager.enableNetwork(id, true);
        boolean connect = mWifiManager.reconnect();
    }

    private WifiConfiguration createWifiConfiguration(ScanResult result, String password, String type) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();
        configuration.SSID = result.SSID;

        if (type.equals(WIFI_TYPE_NOPASS)) {/*无密码*/
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (type.equals(WIFI_TYPE_WEP)) {/*wep*/
            configuration.wepKeys[0] = "\"" + password + "\"";
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            configuration.wepTxKeyIndex = 0;
        } else if (type.equals(WIFI_TYPE_WPA)) {/*wpa*/
            Log.e("wyy", "MainActivity createWifiConfiguration ");
            configuration.preSharedKey = "\"" + password + "\"";
            configuration.hiddenSSID = true;
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            configuration.status = WifiConfiguration.Status.ENABLED;
        }
        return configuration;
    }
}
