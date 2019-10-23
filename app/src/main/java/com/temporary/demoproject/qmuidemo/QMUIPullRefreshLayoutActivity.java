package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUICenterGravityRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIDefaultRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIFollowRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUIPullRefreshLayoutActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.pull_refresh_layout)
    QMUIPullRefreshLayout mPullRefreshLayout;

    private List<String> mDataList = new ArrayList<>();
    private ArrayAdapter mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuipull_refresh_layout);
        ButterKnife.bind(this);

        initTopbar();

        for (int i = 0; i < 26; i++) {
            mDataList.add("item " + i);
        }
        mArrayAdapter = new ArrayAdapter(this, R.layout.customer_textview_layout_two, mDataList);
        mListView.setAdapter(mArrayAdapter);
        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.finishRefresh();
                    }
                }, 3000);
            }
        });
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_pullrefresh_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTopbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final QMUIBottomSheet.BottomListSheetBuilder builder =
                                new QMUIBottomSheet.BottomListSheetBuilder(
                                        QMUIPullRefreshLayoutActivity.this);
                        builder.addItem(getResources().getString(R.string.qmui_pullrefresh_default_offset_calculator));
                        builder.addItem(getResources().getString(R.string.qmui_pullrefresh_follow_offset_calculator));
                        builder.addItem(getResources().getString(R.string.qmui_pullrefresh_center_gravity_offset_calculator));
                        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                switch (position) {
                                    case 0:{
                                        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUIDefaultRefreshOffsetCalculator());
                                        break;
                                    }
                                    case 1:{
                                        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUIFollowRefreshOffsetCalculator());
                                        break;
                                    }
                                    case 2:{
                                        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUICenterGravityRefreshOffsetCalculator());
                                        break;
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.build().show();
                    }
                });
    }
}
