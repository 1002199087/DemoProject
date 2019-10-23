package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Dee on 2018/9/13 0013.
 */

public abstract class QMUIBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeContentView();
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initTopbar();
    }

    public abstract int getContentViewId();
    public abstract void initTopbar();
    public abstract void initBeforeContentView();
}
