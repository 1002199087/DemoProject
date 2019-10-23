package com.temporary.demoproject.qmuidemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.temporary.adapter.SnapHelperAdapter;
import com.temporary.demoproject.R;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUISnapHelperActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.snap_framelayout)
    FrameLayout mSnapFramelayout;

    private SnapHelperAdapter mSnapHelperAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private PagerSnapHelper mPagerSnapHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuisnap_helper);
        ButterKnife.bind(this);

        initTopbar();
        initContent();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_snaphelper_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTopbar.addRightImageButton(R.mipmap.icon_topbar_overflow, QMUIViewHelper.generateViewId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        QMUIBottomSheet.BottomListSheetBuilder builder =
                                new QMUIBottomSheet.BottomListSheetBuilder(QMUISnapHelperActivity
                                        .this);
                        builder.addItem("横向滑动");
                        builder.addItem("竖向滑动");
                        builder.setOnSheetItemClickListener(
                                new QMUIBottomSheet.BottomListSheetBuilder
                                        .OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(QMUIBottomSheet dialog, View itemView,
                                                        int position, String tag) {
                                        mLinearLayoutManager.setOrientation(position == 0 ?
                                                LinearLayoutManager.HORIZONTAL :
                                                LinearLayoutManager.VERTICAL);
                                        dialog.dismiss();
                                    }
                                });
                        builder.build().show();
                    }
                });

        try {
            Socket socket = new Socket("", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initContent() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("item " + i);
        }
        mSnapHelperAdapter = new SnapHelperAdapter(this, list);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView = new RecyclerView(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mSnapHelperAdapter);
        mSnapFramelayout.addView(mRecyclerView);
        mPagerSnapHelper = new PagerSnapHelper();
        mPagerSnapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.e("wyy", "QMUISnapHelperActivity onScrollChange "
                        + mLinearLayoutManager.findFirstVisibleItemPosition());
                mTopbar.setTitle((mLinearLayoutManager.findFirstVisibleItemPosition() + 1) + " / " +
                        "" + 5);
            }
        });
        mRecyclerView.scrollToPosition(2);
    }
}
