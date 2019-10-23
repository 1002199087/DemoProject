package com.temporary.network;

import android.os.Handler;
import android.os.Message;

import com.temporary.demoproject.NetworkApi;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.test.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dee on 2018/7/10.
 */

public class HttpUtils {

    public static void postLogin(String account, String pwd, final Handler handler) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.86.211:8088/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestNetInterface testNetInterface = retrofit.create(TestNetInterface.class);
        Call<PowerCloudDao> call = testNetInterface.getCall(NetworkApi.API_LOGIN, account, pwd);
        call.enqueue(new Callback<PowerCloudDao>() {
            @Override
            public void onResponse(Call<PowerCloudDao> call, Response<PowerCloudDao> response) {
                Message message = handler.obtainMessage();
                //message.obj = response.body().getData();
                //message.what = 101;
                // message.sendToTarget();
            }

            @Override
            public void onFailure(Call<PowerCloudDao> call, Throwable t) {

            }
        });
    }
}
