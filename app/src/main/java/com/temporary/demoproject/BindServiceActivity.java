package com.temporary.demoproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.temporary.demoproject.databinding.ActivityBindServiceBinding;
import com.temporary.service.TestService;

public class BindServiceActivity extends SimpleBaseActivity<ActivityBindServiceBinding> {
    private TestService mTestService;

    @Override
    protected int getContentView() {
        return R.layout.activity_bind_service;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string
                .two_methods_for_starting_service_text));
        mDataBinding.mQmuiTopbar.addLeftBackImageButton().setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        mDataBinding.setActivity(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mOneBtn: {
                Intent intent = new Intent(this, TestService.class);
                intent.putExtra("demo_values", "values");
                startService(intent);
                break;
            }
            case R.id.mTwoBtn: {
                Intent intent = new Intent(this, TestService.class);
                bindService(intent, new TestServiceConnect(), Context.BIND_AUTO_CREATE);
                break;
            }
        }
    }

    class TestServiceConnect implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mTestService = ((TestService.TestBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

}
