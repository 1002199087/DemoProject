package com.temporary.model;

import java.util.List;

/**
 * Created by Dee on 2018/6/19.
 */

public interface ILooperDataModel {
    List<String> getLooperDatas(String path);
    String getChedkedLooperData(List<String> strings, List<Boolean> booleans);
    /*添加温度和湿度*/
    void addTemAndHumToExtroInfo(String path, String temAndHum);
}
