package com.temporary.model;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.temporary.adapter.RecyclerViewAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Dee on 2018/5/21.
 */

public class RecyclerModelImpl implements IRecyclerModel {
    @Override
    public RecyclerViewAdapter getScanResultAdapter(List<ScanResult> list, Context context) {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(list, context);
        return recyclerViewAdapter;
    }

    /*重连wifi*/
    @Override
    public void reconnectWifiAp(WifiManager wifiManager) {
        wifiManager.disconnect();
        WifiInfo info = wifiManager.getConnectionInfo();
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration : list) {
            boolean flag = configuration.toString().contains("validatedInternetAccess");
            if (configuration.SSID != null && flag) {
                //wifiManager.disconnect();
                int id = configuration.networkId;
                if (id > 0) {
                    boolean enable = wifiManager.enableNetwork(id, true);
                }
            } else if (!flag) {
                Log.e("wyy", "RecyclerModelImpl reconnectWifiAp ssid = "
                        + configuration.SSID);
                wifiManager.removeNetwork(configuration.networkId);
            }
        }
    }

    @Override
    public void uploadFileInFlashair(String name) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.86.211:8080/s/status/uploadCheckData";
        MediaType type = MediaType.parse("application/octet-stream");
        File file = new File("/storage/emulated/0/wyy/" + name);
        RequestBody body = RequestBody.create(type, file);
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newBuilder()
                .writeTimeout(5000, java.util.concurrent.TimeUnit.SECONDS)
                .build()
                .newCall(request);
        Log.e("wyy", "RecyclerModelImpl uploadFileInFlashair ");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("wyy", "RecyclerModelImpl onFailure " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("wyy", "RecyclerModelImpl onResponse flag = " + response.isSuccessful());
                String string = response.body().string();
                Log.e("wyy", "RecyclerModelImpl onResponse string = " + string);
                InputStream inputStream = response.body().byteStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                inputStream.close();
                Log.e("wyy", "FlashairModelImpl onResponse stringBuffer " + stringBuffer.toString() + " , string = " + string);
            }
        });
    }
}
