package com.temporary.presenter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.temporary.adapter.LooperDatasAdapter;
import com.temporary.demoproject.viewmodel.ILooperDatasView;
import com.temporary.model.LooperDatasImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Dee on 2018/6/19.
 */

public class LooperDatasPresenter<T> {
    private Context mContext;
    private ILooperDatasView iLooperDatasView;
    private LooperDatasImpl mLooperDatasImpl;
    private LooperDatasAdapter mAdapter;

    public LooperDatasPresenter(T t) {
        this.mContext = (Context) t;
        this.iLooperDatasView = (ILooperDatasView) t;
        this.mLooperDatasImpl = new LooperDatasImpl();
    }

    public void setRecyclerView() {
        mAdapter = new LooperDatasAdapter(mContext,
                mLooperDatasImpl.getLooperDatas(
                        Environment.getExternalStorageDirectory() + "/回路数据.TXT"));
        iLooperDatasView.setRecyclerViewLooperAdapter(mAdapter);
    }

    public void getCheckedLooperDatas() {
        try {
            String string = mLooperDatasImpl.getChedkedLooperData(mAdapter.getList(), mAdapter.getBooleans());
            Log.e("wyy", "LooperDatasPresenter getCheckedLooperDatas string = " + string);
            File saveFile = new File(Environment.getExternalStorageDirectory(), "回路数据.TXT");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream(saveFile), "GBK");
            outputStreamWriter.append(string);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTemAndHumToFile(String path, String temAndHum) {
        mLooperDatasImpl.addTemAndHumToExtroInfo(path, temAndHum);
    }
}
