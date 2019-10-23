package com.temporary.model;

import android.os.Handler;

import com.temporary.bean.ExpressageNetDao;

import rx.Observable;

/**
 * Created by Dee on 2018/9/4 0004.
 */

public interface IExpressageModel {
    void inquireExpressage(String type, String postid, Handler handler);

    Observable<ExpressageNetDao> requestExpressageDao(String type, String postid);
}
