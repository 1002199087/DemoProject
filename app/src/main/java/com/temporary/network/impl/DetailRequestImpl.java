package com.temporary.network.impl;

import android.os.Handler;
import android.os.Message;

import com.temporary.config.NetMessageKey;
import com.temporary.demoproject.NetworkApi;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.network.interactor.IDetailRequest;
import com.temporary.test.DetailResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dee on 2018/7/13.
 */

public class DetailRequestImpl implements IDetailRequest {

    //根据devicecode查询详细信息
    @Override
    public void checkDetail(String deviceCode, final Handler handler) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestNetInterface testNetInterface = retrofit.create(TestNetInterface.class);
        Call<DetailResult> call = testNetInterface.getDetailedResult(NetworkApi.API_GET_DETAIL,
                deviceCode);
        call.enqueue(new Callback<DetailResult>() {
            @Override
            public void onResponse(Call<DetailResult> call, Response<DetailResult> response) {
                Message msg = handler.obtainMessage();
                msg.what = NetMessageKey.KEY_DETAIL;
                msg.obj = response.body();
                msg.sendToTarget();
            }

            @Override
            public void onFailure(Call<DetailResult> call, Throwable t) {
                Message msg = handler.obtainMessage();
                msg.what = NetMessageKey.KEY_DETAIL;
                msg.obj = t.getMessage();
                msg.sendToTarget();
            }
        });
    }
}
