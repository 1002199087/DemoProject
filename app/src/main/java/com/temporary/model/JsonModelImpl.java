package com.temporary.model;

import android.content.Context;
import android.os.Handler;

import com.temporary.network.impl.DetailRequestImpl;
import com.temporary.network.impl.FileRequestImpl;
import com.temporary.network.impl.LoginRequestImpl;

import java.io.File;

/**
 * Created by Dee on 2018/7/13.
 */

public class JsonModelImpl implements IJsonModel {
    private LoginRequestImpl mLoginRequestImpl;
    private DetailRequestImpl mDetailRequestImpl;
    private FileRequestImpl mFileRequestImpl;

    public JsonModelImpl(Context context) {
        mLoginRequestImpl = new LoginRequestImpl(context);
        mDetailRequestImpl = new DetailRequestImpl();
        mFileRequestImpl = new FileRequestImpl();
    }

    /**
     * 登陆
     *
     * @param account
     * @param pwd
     * @param handler
     */
    @Override
    public void login(String account, String pwd, Handler handler) {
        //mLoginRequestImpl.login(account, pwd, handler);
        mFileRequestImpl.downloadFile();
    }

    /**
     * 根据devicecode查询详细信息
     *
     * @param deviceCode
     * @param handler
     */
    @Override
    public void checkDetail(String deviceCode, Handler handler) {
        mDetailRequestImpl.checkDetail(deviceCode, handler);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param json
     * @param handler
     */
    @Override
    public void uploadFile(File file, String json, Handler handler) {
        mFileRequestImpl.uploadFile(file, json, handler);
    }

}
