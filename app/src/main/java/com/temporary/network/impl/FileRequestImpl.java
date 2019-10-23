package com.temporary.network.impl;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.temporary.bean.FileRequestDao;
import com.temporary.config.NetMessageKey;
import com.temporary.demoproject.NetworkApi;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.network.interactor.IFileRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dee on 2018/7/13.
 */

public class FileRequestImpl implements IFileRequest {
    //上传文件
    @Override
    public void uploadFile(File file, String json, final Handler handler) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestNetInterface testNetInterface = retrofit.create(TestNetInterface.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file",
                file.getName(), requestBody);
        RequestBody stringBody = RequestBody.create(MediaType.parse("multipart/form-data"), json);
        Call<FileRequestDao> call = testNetInterface.uploadCheckData(NetworkApi.API_UPLOAD_FILE,
                stringBody, part);
        call.enqueue(new Callback<FileRequestDao>() {
            @Override
            public void onResponse(Call<FileRequestDao> call, Response<FileRequestDao> response) {
                try {
                    Message msg = handler.obtainMessage();
                    msg.what = NetMessageKey.KEY_UPLOAD;
                    msg.obj = response.body();
                    msg.sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FileRequestDao> call, Throwable t) {
                Message msg = handler.obtainMessage();
                msg.what = NetMessageKey.KEY_UPLOAD;
                msg.obj = t.getMessage();
                msg.sendToTarget();
            }
        });
    }

    @Override
    public void downloadFile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.86.188:8088/")
                //.baseUrl("http://mapopen-pub-androidsdk.cdn.bcebos.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestNetInterface testNetInterface = retrofit.create(TestNetInterface.class);
        //Call<ResponseBody> call = testNetInterface.downloadFile("home/故障设备条件.xlsx"/*, "66"*/);
        Call<ResponseBody> call = testNetInterface.downloadFile("s/status/exportingFaultEnclosure", "66");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Log.e("wyy", "FileRequestImpl onResponse " + response.body().byteStream().toString()
                +" , "+getString(response.body().byteStream()));


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory(), "text_img_tttt.xlsx");
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                fos.flush();
                            }
                            fos.close();
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + "/总公司人事管理员-首页.xlsx");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[8192];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private String getString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
