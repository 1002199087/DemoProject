package com.temporary.demoproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.michaelx.loadinglibrary.LoadingLayout;

public class LoadingLayoutActivity extends AppCompatActivity {

    @BindView(R.id.loadLayout)
    LoadingLayout mLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_layout);
        ButterKnife.bind(this);

        /*LoadingLayout loadingLayout = LoadingLayout.wrap(this);
        loadingLayout.showEmpty();*/
        mLoadLayout.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadLayout.loadComplete();
                mLoadLayout.showEmpty();
            }
        }, 3000);
        mLoadLayout.setOnRetryLoadListener(new LoadingLayout.OnRetryLoadListener() {
            @Override
            public void onReLoad() {
                mLoadLayout.showLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLoadLayout.loadComplete();
                        mLoadLayout.showEmpty();
                    }
                }, 3000);
            }
        });
    }
}
