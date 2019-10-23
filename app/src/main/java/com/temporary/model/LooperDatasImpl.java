package com.temporary.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dee on 2018/6/19.
 */

public class LooperDatasImpl implements ILooperDataModel {
    private String mTemStr;
    private String mHumStr;

    @Override
    public List<String> getLooperDatas(String path) {
        try {
            mTemStr = null;
            mHumStr = null;
            List<String> looperDatas = new ArrayList<>();
            int i = 0;
            InputStream inputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                if (string.contains("温度")) {
                    mTemStr = string;
                } else if (string.contains("湿度")) {
                    mHumStr = string;
                } else {
                    if (i % 3 == 0) {
                        stringBuffer.delete(0, stringBuffer.length());
                    }
                    if (i % 3 == 2) {
                        stringBuffer.append(string);
                        looperDatas.add(stringBuffer.toString());
                    } else {
                        stringBuffer.append(string + "\r\n");
                    }
                }
                i++;
            }
            inputStream.close();
            inputStreamReader.close();
            return looperDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getChedkedLooperData(List<String> strings, List<Boolean> booleans) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strings.size(); i++) {
            if (booleans.get(i)) {
                boolean flag = strings.get(i).contains("\n");
                Log.e("wyy", "LooperDatasImpl getChedkedLooperData flag = " + flag);
                stringBuffer.append(strings.get(i) + "\r\n");
            }
        }
        stringBuffer.append(mTemStr + "\r\n" + mHumStr + "\r\n");
        return stringBuffer.toString();
    }

    @Override
    public void addTemAndHumToExtroInfo(String path, String temAndHum) {
        try {
            InputStream inputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "GBK");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String string = null;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string+"\r\n");
            }
            String content = stringBuffer.toString()+temAndHum;
            File saveFile = new File(path);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream(saveFile), "GBK");
            outputStreamWriter.append(content);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
