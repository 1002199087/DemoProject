package com.temporary.demoproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.temporary.demoproject.databinding.ActivityLaunchModeBinding;

import java.lang.ref.WeakReference;

public class LaunchModeActivity extends SimpleBaseActivity<ActivityLaunchModeBinding> {
    @Override
    protected int getContentView() {
        return R.layout.activity_launch_mode;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.launchmodel_title));
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
            case R.id.mStandardBtn: {// standard 模式
                Intent intent = new Intent(this, LaunchModeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.mSingleTopBtn: {// singleTop 模式
                Intent intent = new Intent(this, LaunchModeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            }
            case R.id.mSingleTaskBtn: {// singleTask 模式
                Intent intent = new Intent(this, LaunchModeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
        }
    }

    public String showLog() {
        return this.toString();
    }

    private class LaunchModeHandler extends Handler {
        private WeakReference<Activity> weakReference;

        public LaunchModeHandler(Activity activity) {
            weakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference.get() != null) {

            }
        }
    }
}
