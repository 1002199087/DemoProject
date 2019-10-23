package com.temporary.demoproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.michaelx.loadinglibrary.LoadingLayout;

public class LoadingLibraryActivity extends AppCompatActivity {

    @BindView(R.id.loading_layout)
    LoadingLayout mLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_library);
        ButterKnife.bind(this);

        mLoadingLayout.setLoadingView(R.layout.activity_loading_view);
        mLoadingLayout.showLoading();
        //mLoadingLayout.loadFailure();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mLoadingLayout.showEmpty();
                mLoadingLayout.loadComplete();
            }
        }, 3000);
        mLoadingLayout.setOnRetryLoadListener(new LoadingLayout.OnRetryLoadListener() {
            @Override
            public void onReLoad() {
                mLoadingLayout.setLoadingView(R.layout.activity_loading_view);
                mLoadingLayout.showLoading();
            }
        });
    }
}
