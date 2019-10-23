package com.temporary.network.customer;

import com.temporary.bean.FaultDao;
import com.temporary.bean.FileRequestDao;
import com.temporary.bean.SignatureTokenResponseDao;
import com.temporary.demoproject.NetworkApi;
import com.temporary.test.DetailResult;
import com.temporary.test.PowerCloudDao;
import com.temporary.test.Test;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Dee on 2018/7/5.
 */

public interface TestNetInterface {

    @FormUrlEncoded
    @POST(NetworkApi.API_LOGIN)
    Call<Test> getCall(@Field("account") String account, @Field("password") String password);

    @FormUrlEncoded
    @POST
    Call<PowerCloudDao> getCall(@Url String url, @Field("username") String account, @Field
            ("password") String password);

    @FormUrlEncoded
    @POST
    Call<DetailResult> getDetailedResult(@Url String url, @Field("fId") String fId);

    @Multipart
    @POST
    Call<FileRequestDao> uploadCheckData(@Url String url,
                                         @Part("json") RequestBody json, @Part MultipartBody.Part
                                                 file);

    @FormUrlEncoded
    @POST
    Call<FaultDao> getFaultCall(@Url String url, @Field("deviceClass") String deviceClass);

    //@FormUrlEncoded
    /*@Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url*//*, @Field("fFaultLibraryId") String
    fFaultLibraryId*//*);*/

    @FormUrlEncoded
    @Streaming
    @POST
    Call<ResponseBody> downloadFile(@Url String url, @Field("fFaultLibraryId") String
            fFaultLibraryId);

    @FormUrlEncoded
    @POST
    Call<SignatureTokenResponseDao> getCallForSignatureToken(@Url String url,
                                                             @Field("grant_type") String
                                                                     grant_type, @Field(
            "client_id") String client_id, @Field("client_secret") String
                                                                     client_secret);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> getCallForSignatureView(@Url String url, @Field("access_token") String
            access_token, @Field("image") String image);

    @GET
    @Streaming
    Call<ResponseBody> getNetPic(@Url String url);
}
