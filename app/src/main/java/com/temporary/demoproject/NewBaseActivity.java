package com.temporary.demoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.custom.ConstantValues;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.components.RxActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class NewBaseActivity extends RxActivity implements ConstantValues {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    RelativeLayout mContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_base);

        mContainerView = findViewById(R.id.container_view);

        LayoutInflater.from(this).inflate(getResId(), mContainerView, true);
        ButterKnife.bind(this);
        initTopbar();
        initView();
        init();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getTopbarTitle());
        switch (getTopbarMode()) {
            case ONLY_BACK: {
                mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                break;
            }
            case NO_BAR: {
                mTopbar.setVisibility(View.GONE);
                break;
            }
        }
    }

    public abstract String getTopbarTitle();

    public abstract int getResId();

    public abstract void initView();

    public abstract void init();

    public abstract int getTopbarMode();
}
