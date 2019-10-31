package com.temporary.demoproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.temporary.demoproject.viewmodel.MVVMWithBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MVVMModeActivity extends NewBaseActivity {

    @BindView(R.id.simple_mode_btn)
    Button mSimpleModeBtn;
    @BindView(R.id.with_base_mode_btn)
    Button mWithBaseModeBtn;
    @BindView(R.id.list_for_mvvm_btn)
    Button mRecyclerForMvvmBtn;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MVVMModeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.mvvm_mode_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_mvvm_mode;
    }

    @Override
    public void initView() {

    }

    @Override
    public void init() {

    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.simple_mode_btn, R.id.with_base_mode_btn, R.id.list_for_mvvm_btn})
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.list_for_mvvm_btn: {// RecyclerView 的 MVVM 模式使用
                startActivity(MVVMRecyclerActivity.getIntent(this));
                break;
            }
            case R.id.simple_mode_btn: {// 简单 MVVM 模式
                startActivity(SimpleMVVMModeActivity.getIntent(this));
                break;
            }
            case R.id.with_base_mode_btn: {// 带 Base 的 MVVM 模式
                startActivity(MVVMWithBaseActivity.getIntent(this));
                break;
            }
        }
    }
}
