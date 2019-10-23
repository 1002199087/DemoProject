package com.temporary.network.interactor;

import android.os.Handler;

/**
 * Created by Dee on 2018/7/13.
 */

public interface ILoginRequest {
    //登陆
    void login(String account, String pwd, Handler handler);
}
