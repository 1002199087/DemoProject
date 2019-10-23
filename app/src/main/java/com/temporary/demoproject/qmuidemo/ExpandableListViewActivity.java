package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.adapter.ExpandableAdapter;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableListViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.expand_list_view)
    ExpandableListView mExpandLV;

    private List<String> mGroupList;
    private List<String> mOneList;
    private List<String> mTwoList;
    private List<String> mThreeList;
    private List<List<String>> mItemList;
    private ExpandableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        ButterKnife.bind(this);

        initTopbar();
        initData();

        mAdapter = new ExpandableAdapter(this, mGroupList, mItemList);
        mAdapter.setExpandableListener(new ExpandableAdapter.ExpandableListener() {
            @Override
            public void onClick(int position) {
                Log.e("wyy", "ExpandableListViewActivity onClick " + mGroupList.get(position));
            }
        });
        mExpandLV.setAdapter(mAdapter);
        mExpandLV.setGroupIndicator(null);
    }

    private void initData() {
        mGroupList = new ArrayList<>();
        mGroupList.add("one");
        mGroupList.add("two");
        mGroupList.add("three");

        mOneList = new ArrayList<>();
        mOneList.add("one_1");

        mTwoList = new ArrayList<>();
        for (int i = 0; i < 2; i++)
            mTwoList.add("two_" + i);

        mThreeList = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            mThreeList.add("three_" + i);

        mItemList = new ArrayList<>();
        mItemList.add(mOneList);
        mItemList.add(mTwoList);
        mItemList.add(mThreeList);

    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.ExpandableListView_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
