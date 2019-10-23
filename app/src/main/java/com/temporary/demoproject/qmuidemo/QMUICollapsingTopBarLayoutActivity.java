package com.temporary.demoproject.qmuidemo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.adapter.QDRecyclerViewAdapter;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUICollapsingTopBarLayoutActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout collapsingTopbarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private QDRecyclerViewAdapter mQDRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuicollapsing_top_bar_layout);
        ButterKnife.bind(this);

        initTopbar();

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mQDRecyclerViewAdapter = new QDRecyclerViewAdapter();
        mQDRecyclerViewAdapter.setItemCount(10);
        mRecyclerView.setAdapter(mQDRecyclerViewAdapter);

        collapsingTopbarLayout.setScrimUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });

    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_collapsing_topbar_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
