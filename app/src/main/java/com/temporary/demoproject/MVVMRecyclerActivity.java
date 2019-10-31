package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.databinding.ActivityMvvmRecyclerBinding;
import com.temporary.viewmodel.RecyclerViewModel;

public class MVVMRecyclerActivity extends MVVMBaseActivity<RecyclerViewModel,
        ActivityMvvmRecyclerBinding> {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MVVMRecyclerActivity.class);
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
        mViewModel.initRecycler();
    }

    @Override
    public RecyclerViewModel getViewModel() {
        return new RecyclerViewModel(this);
    }

    @Override
    public int getResId() {
        return R.layout.activity_mvvm_recycler;
    }

    @Override
    public QMUITopBar getTopbar() {
        return mBinding.topbar;
    }

    @Override
    public int getTopbarTitle() {
        return R.string.list_for_mvvm_tip;
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
}
