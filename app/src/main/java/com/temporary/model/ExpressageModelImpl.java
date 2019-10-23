package com.temporary.model;

import android.os.Handler;
import android.os.Message;

import com.temporary.bean.ExpressageNetDao;
import com.temporary.demoproject.NetworkApi;
import com.temporary.factory.RetrofitFactory;
import com.temporary.network.customer.ExpressageNetInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Dee on 2018/9/4 0004.
 */

public class ExpressageModelImpl implements IExpressageModel {

    @Override
    public void inquireExpressage(String type, String postid, final Handler handler) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NetworkApi.API_EXPRESSAGE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ExpressageNetInterface expressageNetInterface = retrofit.create(ExpressageNetInterface
                .class);
        Call<ExpressageNetDao> call = expressageNetInterface
                .getCall(NetworkApi.API_QUERY_URL, type, postid);
        call.enqueue(new Callback<ExpressageNetDao>() {
            @Override
            public void onResponse(Call<ExpressageNetDao> call, Response<ExpressageNetDao>
                    response) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = response.body();
                message.sendToTarget();
            }

            @Override
            public void onFailure(Call<ExpressageNetDao> call, Throwable t) {
                Message message = handler.obtainMessage();
                message.what = 0;
                message.obj = t.getMessage();
                message.sendToTarget();
            }
        });
    }

    @Override
    public Observable<ExpressageNetDao> requestExpressageDao(String type, String postid) {
        return RetrofitFactory.getInstance().getExpressageNetInterface().getExpressageDao
                (NetworkApi.API_QUERY_URL, type, postid);
    }
}
