package com.temporary.model;

import android.content.Context;

import com.temporary.adapter.FlashairAdapter;

import java.util.List;

/**
 * Created by Dee on 2018/5/23.
 */

public interface IFlashairModel {
    FlashairAdapter getFlashairAdapter(List<String> list, Context context);
    String getFlashairFiles();
    boolean downFileFromFlashair(String fileName);
    String delFileOnFlashair(String fileName);
    void uploadFileInFlashair(String name);
}
