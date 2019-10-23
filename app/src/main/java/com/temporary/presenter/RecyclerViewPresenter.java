package com.temporary.presenter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.temporary.adapter.RecyclerViewAdapter;
import com.temporary.demoproject.viewmodel.IRecyclerView;
import com.temporary.model.RecyclerModelImpl;

import java.util.List;

/**
 * Created by Dee on 2018/5/21.
 */

public class RecyclerViewPresenter {
    private Context mContext;
    private IRecyclerView iRecyclerView;
    private RecyclerModelImpl mRecyclerModeImpl;

    public RecyclerViewPresenter(Context context, IRecyclerView iRecyclerView) {
        this.iRecyclerView = iRecyclerView;
        mRecyclerModeImpl = new RecyclerModelImpl();
        mContext = context;
    }

    public void setRecyclerView(List<ScanResult> list) {
        RecyclerViewAdapter adapter = mRecyclerModeImpl.getScanResultAdapter(list, mContext);
        adapter.setViewClickListener(new RecyclerViewAdapter.ViewClickListener() {
            @Override
            public void click(int position) {
                iRecyclerView.editWifiApPassword(position);
            }
        });
        iRecyclerView.updateRecyclerView(adapter);
    }

    public void reconnectWifiAp(final WifiManager wifiManager) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mRecyclerModeImpl.reconnectWifiAp(wifiManager);
            }
        }).start();

    }

    public void uploadFileInFlashair() {
        mRecyclerModeImpl.uploadFileInFlashair("0002.IMA");
    }
}
