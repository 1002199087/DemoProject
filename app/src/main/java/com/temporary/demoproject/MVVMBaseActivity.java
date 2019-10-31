package com.temporary.demoproject;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.custom.ConstantValues;
import com.temporary.demoproject.databinding.ActivityMvvmBaseBinding;
import com.temporary.viewmodel.BaseViewModel;

public abstract class MVVMBaseActivity<M extends BaseViewModel, B extends ViewDataBinding> extends AppCompatActivity implements ConstantValues {
    public B mBinding;
    public M mViewModel;

    public QMUITopBar mTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getResId());

        mViewModel = getViewModel();

        initDataBinding();
        initTopbar();
        init();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar = getTopbar();
        mTopbar.setTitle(getTopbarTitle());
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        switch (getTopbarMode()) {
            case ONLY_BACK: {
                mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onTopbarLeftClicked();
                    }
                });
                break;
            }
        }
    }

    public abstract void initDataBinding();

    public abstract void init();

    public abstract M getViewModel();

    public abstract int getResId();

    public abstract QMUITopBar getTopbar();

    public abstract int getTopbarTitle();

    public abstract int getTopbarMode();

    public abstract void onTopbarLeftClicked();

    public abstract void onTopbarRightClicked();
}
