package com.temporary.demoproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.temporary.presenter.BasePresenter;

import butterknife.ButterKnife;

public abstract class BaseActivity<V, P extends BasePresenter> extends AppCompatActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        initTopbar();
        mPresenter = getPresenter();
        mPresenter.attach((V)this);
    }

    protected abstract void initTopbar();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }

    protected abstract int getContentViewId();

    protected abstract P getPresenter();
}
