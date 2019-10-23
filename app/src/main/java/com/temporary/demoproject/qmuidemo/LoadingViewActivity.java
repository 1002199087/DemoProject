package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mingle.widget.LoadingView;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingViewActivity extends AppCompatActivity {

    @BindView(R.id.loadView)
    LoadingView mLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_view);
        ButterKnife.bind(this);

        mLoadView.setVisibility(View.GONE);
    }
}
