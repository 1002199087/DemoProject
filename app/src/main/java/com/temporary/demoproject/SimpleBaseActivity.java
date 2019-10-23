package com.temporary.demoproject;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wyy on 2019/3/11 0011.
 */

public abstract class SimpleBaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    public T mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        mDataBinding = DataBindingUtil.setContentView(this, getContentView());
        initTopbar();
        initView();
    }

    protected abstract int getContentView();
    protected abstract void initTopbar();
    protected abstract void initView();
}
