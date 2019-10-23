package com.temporary.network.impl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.temporary.bean.SignatureTokenResponseDao;
import com.temporary.config.NetMessageKey;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.network.interactor.ISignatureRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wyy on 2019/2/21 0021.
 */

public class SignatureRequestImpl implements ISignatureRequest {
    private OkHttpClient client;
    private Retrofit retrofit;
    private TestNetInterface mNetService;

    public SignatureRequestImpl() {
        this.client = new OkHttpClient.Builder().connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS).build();
        this.retrofit = new Retrofit.Builder().baseUrl(NetMessageKey.SIGNATURE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build();
        this.mNetService = retrofit.create(TestNetInterface.class);
    }

    @Override
    public void requestSignatureToken(String grant_type, String client_id, String client_secret,
                                      final Handler handler, final int flag) {
        Call<SignatureTokenResponseDao> call = mNetService.getCallForSignatureToken(NetMessageKey
                .SIGNATURE_TOKEN_URL, grant_type, client_id, client_secret);
        call.enqueue(new Callback<SignatureTokenResponseDao>() {
            @Override
            public void onResponse(Call<SignatureTokenResponseDao> call,
                                   Response<SignatureTokenResponseDao> response) {
                Message message = handler.obtainMessage();
                message.what = flag;
                message.obj = response.body();
                message.sendToTarget();
            }

            @Override
            public void onFailure(Call<SignatureTokenResponseDao> call, Throwable t) {

            }
        });
    }

    @Override
    public void requestSignatureString(String access_token, String image, Handler handler, int
            flag) {
        Call<ResponseBody> call = mNetService.getCallForSignatureView(NetMessageKey
                .SIGNATURE_BITMAP_URL, access_token, image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("wyy", "SignatureRequestImpl onResponse " + getString(response.body()
                        .byteStream()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("wyy", "SignatureRequestImpl onFailure ");
            }
        });
    }

    private String getString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
