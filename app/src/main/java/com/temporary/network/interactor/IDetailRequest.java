package com.temporary.network.interactor;

import android.os.Handler;

/**
 * Created by Dee on 2018/7/13.
 */

public interface IDetailRequest {
    //根据devicecode查询详细信息
    void checkDetail(String deviceCode, Handler handler);
}
