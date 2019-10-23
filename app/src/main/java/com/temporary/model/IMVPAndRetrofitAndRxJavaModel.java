package com.temporary.model;

import com.temporary.bean.ExpressageNetDao;

import rx.Observable;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

public interface IMVPAndRetrofitAndRxJavaModel {
    Observable<ExpressageNetDao> getExpressageInfo(String type, String postid);
}
