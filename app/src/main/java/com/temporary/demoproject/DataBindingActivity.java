package com.temporary.demoproject;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.adapter.DataBindingAdapter;
import com.temporary.bean.PeopleDao;
import com.temporary.demoproject.databinding.ActivityDataBindingBinding;

import java.util.ArrayList;
import java.util.List;

public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBindingBinding mDataBinding;
    private PeopleDao mPeopleDao;

    private DataBindingAdapter mAdapter;
    private List<String> mItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout
                .activity_data_binding);

        mItemList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            mItemList.add("wyy");
        }

        mPeopleDao = new PeopleDao();
        mPeopleDao.setName("wyy");
        mPeopleDao.setSex("man");
        mPeopleDao.setAge(mItemList.size());
        mDataBinding.setPeople(mPeopleDao);
        mDataBinding.setActivity(this);

        mAdapter = new DataBindingAdapter(this, mItemList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.dataRecycler.setLayoutManager(layoutManager);
        mDataBinding.dataRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (mDataBinding.dataRecycler.getChildAdapterPosition(view) == 0) {
                    outRect.top = QMUIDisplayHelper.dp2px(DataBindingActivity.this, 10);
                }
                outRect.left = QMUIDisplayHelper.dp2px(DataBindingActivity.this, 10);
                outRect.right = QMUIDisplayHelper.dp2px(DataBindingActivity.this, 10);
                outRect.bottom = QMUIDisplayHelper.dp2px(DataBindingActivity.this, 10);
            }
        });
        mDataBinding.dataRecycler.setAdapter(mAdapter);

        initTopBar();
    }

    private void initTopBar() {
        QMUIStatusBarHelper.translucent(this);
        mDataBinding.mQmuiTopbar.setTitle(getResources().getString(R.string.databingding_title));
        mDataBinding.mQmuiTopbar.addLeftBackImageButton().setOnClickListener(new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_button: {
                PeopleDao dao = new PeopleDao();
                mPeopleDao.setName(dao.getName());

                int age = mPeopleDao.getAge();
                mItemList.add(String.valueOf(age));
                mAdapter.notifyDataSetChanged();
                mDataBinding.dataRecycler.scheduleLayoutAnimation();

                mPeopleDao.setAge(mItemList.size());
                break;
            }
            case R.id.two_button: {
                if (mItemList.size() > 0) {
                    mItemList.remove(mItemList.size() - 1);
                }
                mAdapter.notifyDataSetChanged();
                mDataBinding.dataRecycler.scheduleLayoutAnimation();

                mPeopleDao.setAge(mItemList.size());
                break;
            }
        }
    }
}
