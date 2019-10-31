package com.temporary.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.temporary.bean.response.SimpeResponseDao;

import java.util.Random;

public class SimpleViewModel {
    private Context mContext;
    public ObservableField<SimpeResponseDao> mResponseDao = new ObservableField<>();

    public SimpleViewModel(Context mContext) {
        this.mContext = mContext;
        SimpeResponseDao dao = new SimpeResponseDao();
        dao.setCode("0000");
        dao.setMsg("成功");
        mResponseDao.set(dao);
    }

    public void updateTextView() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(mResponseDao.get());
        SimpeResponseDao dao = gson.fromJson(content, SimpeResponseDao.class);
        Random random = new Random();
        dao.setMsg("updateTextView: " + (random.nextInt(10000) + 1));
        mResponseDao.set(dao);
    }
}
