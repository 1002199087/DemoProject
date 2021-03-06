package com.temporary.factory;

import com.temporary.demoproject.NetworkApi;
import com.temporary.network.customer.ExpressageNetInterface;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

public class RetrofitFactory {
    private static RetrofitFactory instance;

    private final String JSON_MEDIA_TYPE = "application/json; charset=utf-8";
    private final String FILE_MEDIA_TYPE = "multipart/form-data";

    public static synchronized RetrofitFactory getInstance() {
        if (instance == null) {
            synchronized (RetrofitFactory.class) {
                instance = new RetrofitFactory();
                return instance;
            }
        }
        return instance;
    }

    public ExpressageNetInterface getExpressageNetInterface() {
        return getRetrofit().create(ExpressageNetInterface.class);
    }

    public ExpressageNetInterface getSpringBootInterface() {
        return getSpringBootRetrofit().create(ExpressageNetInterface.class);
    }

    public Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit
                .SECONDS).writeTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkApi.API_EXPRESSAGE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public Retrofit getSpringBootRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit
                .SECONDS).writeTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkApi.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public RequestBody getJsonRequestBody(String content) {
        RequestBody body = RequestBody.create(MediaType.parse(JSON_MEDIA_TYPE), content);
        return body;
    }

    public RequestBody getFileRequestBody(String content) {
        RequestBody body = RequestBody.create(MediaType.parse(FILE_MEDIA_TYPE), content);
        return body;
    }

    public RequestBody getFileRequestBody(File file) {
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        return body;
    }
}
