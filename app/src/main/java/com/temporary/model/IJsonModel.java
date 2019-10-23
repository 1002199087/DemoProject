package com.temporary.model;

import android.os.Handler;

import java.io.File;

/**
 * Created by Dee on 2018/7/13.
 */

public interface IJsonModel {

    //登陆
    void login(String account, String pwd, Handler handler);

    //根据devicecode查询详细信息
    void checkDetail(String deviceCode, Handler handler);

    //上传文件
    void uploadFile(File file, String json, Handler handler);
}
