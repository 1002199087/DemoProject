package com.temporary.presenter;

import android.content.Context;
import android.os.Message;

import com.temporary.bean.FileRequestDao;
import com.temporary.config.NetMessageKey;
import com.temporary.demoproject.viewmodel.IJsonView;
import com.temporary.model.JsonModelImpl;
import com.temporary.test.DetailResult;
import com.temporary.test.PowerCloudDao;
import com.temporary.test.Test;

import java.io.File;

/**
 * Created by Dee on 2018/7/13.
 */

public class JsonPresenter extends BasePresenter<IJsonView> {
    private JsonModelImpl mJsonModelImpl;

    public JsonPresenter(Context context) {
        this.mJsonModelImpl = new JsonModelImpl(context);
    }

    //登陆
    public void login(String account, String pwd) {
        mJsonModelImpl.login(account, pwd, getHandler());
    }

    //根据devicecode查询详细信息
    public void checkDetail(String deviceCode) {
        mJsonModelImpl.checkDetail(deviceCode, getHandler());
    }

    //上传文件
    public void uploadFile(File file, String json) {
        mJsonModelImpl.uploadFile(file, json, getHandler());
    }

    @Override
    public void handleNetMessage(Message msg) {
        super.handleNetMessage(msg);
        switch (msg.what) {
            case NetMessageKey.KEY_LOGIN: {//登陆
                if (msg.obj instanceof Test) {
                    Test test = (Test) msg.obj;
                    Test.Data data = test.getData();
                    if (getObtainView() != null)
                        getObtainView().refreshResult(data.getfAccount());
                } else if (msg.obj instanceof PowerCloudDao) {
                    if (getObtainView() != null) {
                        getObtainView().refreshResult("success!");
                    }
                } else {
                    String string = (String) msg.obj;
                    if (getObtainView() != null)
                        getObtainView().refreshResult(string);
                }
                break;
            }
            case NetMessageKey.KEY_DETAIL: {//根据devicecode查询详细信息
                if (msg.obj instanceof DetailResult) {
                    DetailResult detailResult = (DetailResult) msg.obj;
                    if (getObtainView() != null)
                        getObtainView().refreshResult(detailResult.getData().getfMaintainproject());
                } else {
                    String message = (String) msg.obj;
                    if (getObtainView() != null)
                        getObtainView().refreshResult(message);
                }
                break;
            }
            case NetMessageKey.KEY_UPLOAD: {//上传文件
                if (msg.obj instanceof FileRequestDao) {
                    FileRequestDao dao = (FileRequestDao) msg.obj;
                    getObtainView().refreshResult(String.valueOf(dao.getStatusText()));
                } else {
                    String eMessage = (String) msg.obj;
                    getObtainView().refreshResult(eMessage);
                }
                break;
            }
        }
    }
}
