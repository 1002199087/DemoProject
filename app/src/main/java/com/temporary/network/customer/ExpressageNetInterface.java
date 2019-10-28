package com.temporary.network.customer;

import com.temporary.bean.EventBusDao;
import com.temporary.bean.ExpressageNetDao;
import com.temporary.bean.response.SimpeResponseDao;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Dee on 2018/9/4 0004.
 */

public interface ExpressageNetInterface {
    @FormUrlEncoded
    @POST
    Call<ExpressageNetDao> getCall(@Url String url, @Field("type") String type, @Field("postid")
            String postid);

    @FormUrlEncoded
    @POST
    Observable<ExpressageNetDao> getExpressageDao(@Url String url, @Field("type") String type,
                                                  @Field("postid") String postid);

    /**
     * 获取json格式的内容，不带参数
     *
     * @param url
     * @return
     */
    @GET
    Observable<EventBusDao> requestJsonContent(@Url String url);

    /**
     * 获取json格式的内容，带参数
     *
     * @param url
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<EventBusDao> requestJsonContent(@Url String url, @Field("name") String name,
                                               @Field("age") int age, @Field("sex") String sex);

    /**
     * 获取json格式的内容，带参数，url带id
     *
     * @param url
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<EventBusDao> requestJsonContent(@Url String url, @Field("name") String name);

    /**
     * 获取json格式的内容，带json参数
     *
     * @param url
     * @param params
     * @return
     */
    @POST
    Observable<SimpeResponseDao> requestJsonContentWithJsonParams(@Url String url,
                                                                  @Body RequestBody params);

    /**
     * 上传文件
     *
     * @param url
     * @param map
     * @return
     */
    @Multipart
    @POST
    Observable<SimpeResponseDao> requestUploadFile(@Url String url,
                                                   @PartMap Map<String, RequestBody> map);

    /**
     * 上传参数和文件
     *
     * @param url
     * @param body
     * @param map
     * @return
     */
    @Multipart
    @POST
    Observable<SimpeResponseDao> requestUploadParamsAndFiles(@Url String url,
                                                             @Part("params") RequestBody body,
                                                             @PartMap Map<String, RequestBody> map);
}
