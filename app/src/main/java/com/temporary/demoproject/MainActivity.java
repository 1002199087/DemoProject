package com.temporary.demoproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.temporary.adapter.RecyclerViewAdapter;
import com.temporary.demoproject.viewmodel.IRecyclerView;
import com.temporary.presenter.RecyclerViewPresenter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IRecyclerView {
    @BindView(R.id.demo_recyclerview)
    public RecyclerView mRecyclerView;
    @BindView(R.id.recyclerview_button)
    public Button mButton;
    @BindView(R.id.flashair_button)
    public Button mFlashairBtn;
    @BindView(R.id.wifi_info_button)
    public Button mWifiInfoBtn;
    @BindView(R.id.connecting_ap_textview)
    public TextView mConnectingApTV;
    @BindView(R.id.content_textview)
    TextView mContentTV;
    private RecyclerViewReceiver mRecyclerViewReceiver;
    private WifiManager mWifiManager;
    private RecyclerViewPresenter mPresenter;
    private List<ScanResult> mScanResultslist;
    private List<WifiConfiguration> mWifiConfigurationsList;
    private static final String WIFI_TYPE_NOPASS = "nopass";
    private static final String WIFI_TYPE_WEP = "wep";
    private static final String WIFI_TYPE_WPA = "wpa";
    private ProgressDialog mProgressDialog;

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private List<ScanResult> mScanResultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //requestPermissions();

        checkPermission();
        init();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .ACCESS_WIFI_STATE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .CHANGE_WIFI_STATE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .ACCESS_COARSE_LOCATION},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .ACCESS_FINE_LOCATION},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest
                .permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .INTERNET},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                .READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .READ_EXTERNAL_STORAGE},
                    0);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                .WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                            .WRITE_EXTERNAL_STORAGE},
                    0);
        }
    }

    private void init() {
        mRecyclerViewReceiver = new RecyclerViewReceiver();
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mPresenter = new RecyclerViewPresenter(getApplicationContext(), this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        getApplicationContext().registerReceiver(mRecyclerViewReceiver, intentFilter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.wifi_ap_connect_progress_msg));
        mProgressDialog.setCancelable(true);

        mScanResultList = new ArrayList<>();
        mRecyclerViewAdapter = new RecyclerViewAdapter(mScanResultList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void checkPermission() {
        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_WIFI_STATE) != PackageManager
                        .PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CHANGE_WIFI_STATE) != PackageManager
                        .PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                        .PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                        .PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                        .PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .ACCESS_WIFI_STATE,
                    Manifest.permission.CHANGE_WIFI_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        WifiInfo info = mWifiManager.getConnectionInfo();
        updateConnectingApTV(info);
    }

    private void updateConnectingApTV(WifiInfo info) {
        String result = String.format(getResources().getString(R.string.wifi_ap_connecting_ap_text),
                info.getSSID());
        mConnectingApTV.setText(result);
    }

    @Override
    public void updateRecyclerView(RecyclerViewAdapter adapter) {
        //mRecyclerView.setAdapter(adapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mScanResultList = adapter.get
    }

    @Override
    public void connectWifiAp(int position, String password) {/*连接指定热点*/
        mWifiManager.disconnect();
        String ssid = mScanResultslist.get(position).SSID;
        String type = mScanResultslist.get(position).capabilities;
        WifiConfiguration config = createWifiConfiguration(ssid, password,
                WIFI_TYPE_WPA);//WIFI_TYPE_WPA econweb1 12345678
        int id = mWifiManager.addNetwork(config);
        boolean enable = mWifiManager.enableNetwork(id, true);
        boolean connect = mWifiManager.reconnect();
        mProgressDialog.show();
    }

    @Override
    public void editWifiApPassword(final int position) {
        String ssid = mScanResultslist.get(position).SSID;
        View editView = LayoutInflater.from(this).inflate(R.layout.activity_wifi_password_edittext,
                null, false);
        final EditText editText = (EditText) editView.findViewById(R.id.wifi_password_edittext);

        new AlertDialog.Builder(this)
                .setView(editView)
                .setTitle(ssid + " " + getResources().getString(R.string.wifi_ap_password_title))
                .setPositiveButton(getResources().getString(R.string.wifi_ap_ok_title),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String password = editText.getText().toString();
                                connectWifiAp(position, password);
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.wifi_ap_cancel_title), null)
                .create()
                .show();
    }


    private WifiConfiguration createWifiConfiguration(String ssid, String password, String type) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();
        configuration.SSID = "\"" + ssid + "\"";

        if (type.equals(WIFI_TYPE_NOPASS)) {/*无密码*/
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (type.equals(WIFI_TYPE_WEP)) {/*wep*/
            configuration.wepKeys[0] = "\"" + password + "\"";
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            configuration.wepTxKeyIndex = 0;
        } else if (type.equals(WIFI_TYPE_WPA)) {/*wpa*/
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

    @OnClick({R.id.recyclerview_button, R.id.flashair_button, R.id.wifi_info_button})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.recyclerview_button: {/*显示 wifi 热点列表*/
                boolean fineFlag = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager
                        .PERMISSION_GRANTED;
                Log.e("wyy", "MainActivity onClick sub2 " + "get 主回路数据.TXT".getBytes().length + "" +
                        " , " +
                        "huilushuju.TXT".getBytes().length);
                byte[] testBytes = "get 主回路数据.TXT".getBytes();
                for (int i = 0; i < testBytes.length; i++) {
                    byte[] bytes = new byte[20];
                    if (i <= 19) {
                        bytes[i] = testBytes[i];
                    } else if (i == 20) {
                        try {
                            Log.e("wyy", "MainActivity onClick " + new String(testBytes, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    String mainString = "get 主回路数据_主回路数据_主回路数据.TXT";
                    String subString;
                    while (mainString.getBytes().length > 20) {
                        subString = bSubstring(mainString, 20);
                        Log.e("wyy", "MainActivity onClick sss " + subString);
                        mainString = mainString.substring(subString
                                .length(), mainString.length());
                        Log.e("wyy", "MainActivity onClick sss " + mainString);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                boolean isStartScan = mWifiManager.startScan();
                mContentTV.setText("位置权限：" + fineFlag + " , 已经开始搜索：" + isStartScan);
                /*Intent GPSIntent = new Intent();
                GPSIntent.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
                GPSIntent.setData(Uri.parse("custom:3"));*/


                break;
            }
            case R.id.flashair_button: {/*显示 Flashair SD卡 文件列表*/
                Intent intent = new Intent(this, FlashairActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.wifi_info_button: {/*断网重连*/
                String result = String.format(getResources().getString(R.string
                                .wifi_ap_connecting_ap_text),
                        "");
                mConnectingApTV.setText(result);
                mPresenter.reconnectWifiAp(mWifiManager);
                //mPresenter.uploadFileInFlashair();
                //recreateNameForFile();
                break;
            }
        }
    }

    public static String bSubstring(String s, int length) throws Exception {

        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        for (; i < bytes.length && n < length; i++) {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1) {
                n++; // 在UCS2第二个字节时n加1
            } else {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0) {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1)

        {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0)
                i = i - 1;
                // 该UCS2字符是字母或数字，则保留该字符
            else
                i = i + 1;
        }
        return new String(bytes, 0, i, "Unicode");
    }

    private void recreateNameForFile() {
        String fromName = "外施.txt_1528443122156_外施交流耐压试验_18M00000002001945.txt";
        String[] strings = fromName.split("_");
        String[] formats = fromName.split("\\.");

        long time = Long.valueOf(strings[strings.length - 3]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String result = simpleDateFormat.format(time);
        fromName = strings[strings.length - 2] + "_" + result + "." + formats[formats.length - 1];
        Log.e("wyy", "MainActivity recreateNameForFile fromName = " + fromName + " , result = " +
                result);
    }

    private void createFile(byte[] bytes) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "外施.txt");
            FileOutputStream fileOutputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
            fileOutputStream.close();
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*验证 UTF-8 GBK 解码区别*/
    private void saveAndReadFile() {
        try {
            String exampleString = "知道不知道";
            byte[] bytes = exampleString.getBytes("UTF-8");
            createFile(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/外施.txt");
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                    "GBK");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String s = null;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            inputStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean getIsexistSimFlag() {
        TelephonyManager telecomManager = (TelephonyManager) getSystemService(Context
                .TELEPHONY_SERVICE);
        int simState = telecomManager.getSimState();
        boolean flag = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT: {
                flag = false;
                break;
            }
            case TelephonyManager.SIM_STATE_UNKNOWN: {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private class RecyclerViewReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            mContentTV.setText(action);
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
                mScanResultslist = mWifiManager.getScanResults();
                mWifiConfigurationsList = mWifiManager.getConfiguredNetworks();
                for (WifiConfiguration configuration : mWifiConfigurationsList) {

                }
                mScanResultList.clear();
                for (int i = 0; i < mScanResultslist.size(); i++) {
                    mScanResultList.add(mScanResultslist.get(i));
                }
                Log.e("wyy", "RecyclerViewReceiver onReceive sss" + mScanResultList.size());
                mRecyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "WiFi数量：" + mScanResultslist.size(), Toast
                        .LENGTH_SHORT).show();
                mPresenter.setRecyclerView(mScanResultslist);
            } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                WifiInfo info = mWifiManager.getConnectionInfo();
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    mProgressDialog.cancel();
                    updateConnectingApTV(info);
                }
            }
        }
    }

}
