package com.temporary.viewmodel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.temporary.adapter.MVVMRecyclerAdapter;
import com.temporary.bean.PeopleDao;
import com.temporary.demoproject.MVVMRecyclerActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewModel extends BaseViewModel {
    private Context mContext;
    private MVVMRecyclerActivity mActivity;
    private MVVMRecyclerAdapter mAdapter;

    public RecyclerViewModel(MVVMRecyclerActivity activity) {
        this.mContext = activity.getApplicationContext();
        mActivity = activity;

    }

    public void initRecycler() {
        List<PeopleDao> daos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PeopleDao peopleDao = new PeopleDao();
            peopleDao.setName("name-000" + i);
            peopleDao.setAge(i);
            peopleDao.setSex("sex-" + i);
            daos.add(peopleDao);
        }
        mAdapter = new MVVMRecyclerAdapter(mContext, daos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivity.mBinding.itemRecycler.setLayoutManager(layoutManager);
        mActivity.mBinding.itemRecycler.setAdapter(mAdapter);
    }
}
