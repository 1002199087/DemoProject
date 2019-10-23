package com.temporary.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.temporary.adapter.FlashairAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Dee on 2018/5/23.
 */

public class FlashairModelImpl implements IFlashairModel {
    @Override
    public FlashairAdapter getFlashairAdapter(List<String> list, Context context) {
        FlashairAdapter adapter = new FlashairAdapter(list, context);
        return adapter;
    }

    /*获取 Flashair SD卡上文件列表*/
    @Override
    public String getFlashairFiles() {
        String commond = "http://flashair/command.cgi?op=100&DIR=/";
        //String commond = "http://flashair/command.cgi?op=100＆DIR=/DCIM";
        try {
            URL url = new URL(commond);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*下载 Flashair SD卡 指定文件*/
    @Override
    public boolean downFileFromFlashair(String fileName) {
        String commond = "http://flashair/" + fileName;
        try {
            URL url = new URL(commond);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory() + "/wyy/"
                    + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*删除 wifi sdcard 内指定文件
    * 注意：必须先修改 wifi sdcard 内配置文件 /SD_WLAN/CONFIG （次文件被隐藏，先设置“显示隐藏文件”,使用记事本打开）
    * 在“VENDOR=XXXXXX”下面加一行：“UPLOAD=1”，然后再插拔 wifi sdcard ，此功能才生效
    * */
    @Override
    public String delFileOnFlashair(String fileName) {
        String commond = "http://flashair/upload.cgi?DEL=/" + fileName;
        try {
            URL url = new URL(commond);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            inputStream.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    public void uploadFileInFlashair(String name) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.86.211:8080/s/status/uploadCheckData";
        MediaType type = MediaType.parse("application/octet-stream");
        File file = new File("/storage/emulated/0/wyy/" + name);
        RequestBody body = RequestBody.create(type, file);
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newBuilder()
                .writeTimeout(50, java.util.concurrent.TimeUnit.SECONDS)
                .build()
                .newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                InputStream inputStream = response.body().byteStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                inputStream.close();
                Log.e("wyy", "FlashairModelImpl onResponse stringBuffer " + stringBuffer.toString() + " , string = " + string);
            }
        });
    }
}
