package com.temporary.network.interactor;

import android.os.Handler;

import java.io.File;

/**
 * Created by Dee on 2018/7/13.
 */

public interface IFileRequest {
    //上传文件
    void uploadFile(File file, String json, Handler handler);

    void downloadFile();
}
