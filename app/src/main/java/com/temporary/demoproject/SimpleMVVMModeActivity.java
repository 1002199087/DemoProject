package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.temporary.demoproject.databinding.ActivitySimpleMvvmmodeBinding;
import com.temporary.viewmodel.SimpleViewModel;
import com.vise.log.ViseLog;


public class SimpleMVVMModeActivity extends AppCompatActivity {
    private ActivitySimpleMvvmmodeBinding mBinding;
    private SimpleViewModel mViewModel;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SimpleMVVMModeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_simple_mvvmmode);
        mViewModel = new SimpleViewModel(this);
        mBinding.setActivity(this);
        mBinding.setViewModel(mViewModel);

        initTopbar();
    }

    private void initTopbar() {
        mBinding.topbar.setTitle(getString(R.string.simple_mode_tip));
        mBinding.topbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        QMUIStatusBarHelper.translucent(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.request_btn: {
                mViewModel.updateTextView();
                break;
            }
        }
    }
}
