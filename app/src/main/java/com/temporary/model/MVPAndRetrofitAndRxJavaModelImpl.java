package com.temporary.model;

import com.temporary.bean.ExpressageNetDao;
import com.temporary.demoproject.NetworkApi;
import com.temporary.factory.RetrofitFactory;

import rx.Observable;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

public class MVPAndRetrofitAndRxJavaModelImpl implements IMVPAndRetrofitAndRxJavaModel {
    @Override
    public Observable<ExpressageNetDao> getExpressageInfo(String type, String postid) {
        return RetrofitFactory.getInstance().getExpressageNetInterface().getExpressageDao
                (NetworkApi.API_QUERY_URL, type, postid);
    }
}
