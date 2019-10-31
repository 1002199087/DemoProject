package com.temporary.demoproject.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.MVVMBaseActivity;
import com.temporary.demoproject.R;
import com.temporary.demoproject.databinding.ActivityMvvmWithBaseBinding;
import com.temporary.util.QMUITipDialogUtil;
import com.temporary.viewmodel.WithBaseViewModel;
import com.vise.log.ViseLog;

public class MVVMWithBaseActivity extends MVVMBaseActivity<WithBaseViewModel,
        ActivityMvvmWithBaseBinding> implements WithBaseViewModel.WithBaseViewModelListener {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MVVMWithBaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public void initDataBinding() {
        mBinding.setActivity(this);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public void init() {

    }

    @Override
    public WithBaseViewModel getViewModel() {
        return new WithBaseViewModel(this, this);
    }

    @Override
    public int getResId() {
        return R.layout.activity_mvvm_with_base;
    }

    @Override
    public QMUITopBar getTopbar() {
        return mBinding.topbar;
    }

    @Override
    public int getTopbarTitle() {
        return R.string.base_mode_tip;
    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }

    @Override
    public void onTopbarLeftClicked() {
        finish();
    }

    @Override
    public void onTopbarRightClicked() {

    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.request_btn: {
                mViewModel.requestJsonContent();
                break;
            }
        }
    }

    @Override
    public void onSuccessForLoadingListener() {
        QMUITipDialogUtil.showSuccessDialog(this, R.string.success_for_loading_tip,
                mBinding.topbar);
    }
}
