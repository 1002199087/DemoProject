package com.temporary.network.customer;

import com.temporary.bean.ExpressageNetDao;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
}
