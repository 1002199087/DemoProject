package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.adapter.SwipeMenuAdapter;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeMenuLayoutActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.demo_recyclerview)
    RecyclerView mDemoRV;

    private List<String> mList;
    private SwipeMenuAdapter mSwipeMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_layout);
        ButterKnife.bind(this);

        initTopbar();

        mList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            mList.add("item " + i);
        }
        mSwipeMenuAdapter = new SwipeMenuAdapter(this, mList);
        mSwipeMenuAdapter.setISwipeMenuAdapter(new SwipeMenuAdapter.ISwipeMenuAdapter() {
            @Override
            public void onItemClick(int position) {
                // mSwipeMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDelClick(int position) {
                mSwipeMenuAdapter.getList().remove(position);
                mDemoRV.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeMenuAdapter.notifyDataSetChanged();
                    }
                }, 500);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);//StaggeredGridLayoutManager

        mDemoRV.setLayoutManager(layoutManager);
        mDemoRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mDemoRV.setItemAnimator(new DefaultItemAnimator());
        mDemoRV.setAdapter(mSwipeMenuAdapter);

        int animRes = R.anim.layout_bottom_in;
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this,
                animRes);
        mDemoRV.setLayoutAnimation(animationController);
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.swipe_menu_layout_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
