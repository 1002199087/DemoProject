package com.temporary.network.impl;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.temporary.bean.FaultDao;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.network.interactor.ILoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dee on 2018/7/13.
 */

public class LoginRequestImpl implements ILoginRequest {
    private Context mContext;
    private final String MY_URL = "http://192.168.86.191:8088/";
    private final String MY_FAULT_URL = "s/status/searchEquipmentTypes";

    public LoginRequestImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void login(String account, String pwd, final Handler handler) {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(NetworkApi.API_PC_URL)
                .baseUrl(MY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestNetInterface testNetInterface = retrofit.create(TestNetInterface.class);

        Call<FaultDao> call = testNetInterface.getFaultCall(MY_FAULT_URL, "变电设备");
        call.enqueue(new Callback<FaultDao>() {
            @Override
            public void onResponse(Call<FaultDao> call, Response<FaultDao> response) {
                FaultDao faultDao = (FaultDao) response.body();
                Log.e("wyy", "LoginRequestImpl onResponse " + faultDao.getData().length);
            }

            @Override
            public void onFailure(Call<FaultDao> call, Throwable t) {
                Log.e("wyy", "LoginRequestImpl onFailure " + t.toString());
            }
        });

        /*Call<PowerCloudDao> call = testNetInterface.getCall(NetworkApi.API_PC_LOGIN,
                account, pwd);
        call.enqueue(new Callback<PowerCloudDao>() {
            @Override
            public void onResponse(Call<PowerCloudDao> call, Response<PowerCloudDao> response) {
                Message msg = handler.obtainMessage();
                msg.what = NetMessageKey.KEY_LOGIN;
                if (((PowerCloudDao) response.body()).getAccess_token() == null) {
                    msg.obj = ((PowerCloudDao) response.body()).getStatusText();
                } else {
                    msg.obj = response.body();
                }
                //Log.e("wyy", "LoginRequestImpl onResponse aaa " + ((PowerCloudDao) msg.obj).getAccess_token());
                msg.sendToTarget();
            }

            @Override
            public void onFailure(Call<PowerCloudDao> call, Throwable t) {
                Message msg = handler.obtainMessage();
                msg.what = NetMessageKey.KEY_LOGIN;
                msg.obj = t.getMessage();
                Log.e("wyy", "LoginRequestImpl onResponse bbb " + t.getMessage());
                msg.sendToTarget();
            }
        });*/
    }
}
