package com.temporary.demoproject.viewmodel;

import com.temporary.adapter.RecyclerViewAdapter;

/**
 * Created by Dee on 2018/5/21.
 */

public interface IRecyclerView {
    void updateRecyclerView(RecyclerViewAdapter adapter);
    void connectWifiAp(int position, String password);
    void editWifiApPassword(int position);
}
