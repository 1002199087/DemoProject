package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.temporary.custom.ConstantValues;
import com.temporary.custom.CustomerBtnLayout;
import com.temporary.util.QMUITipDialogUtil;

import butterknife.BindView;

public class NewBaseDemoActivity extends NewBaseActivity implements ConstantValues {
    @BindView(R.id.customer_btn_layout)
    CustomerBtnLayout mCustomerBtnLayout;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, NewBaseDemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.new_baseactivity);
    }

    @Override
    public int getResId() {
        return R.layout.activity_new_base_demo;
    }

    @Override
    public void initView() {
        mCustomerBtnLayout.setCustomerBtnLayoutListener(new CustomerBtnLayout
        .CustomerBtnLayoutListener() {
            @Override
            public void onLeftClick() {
                QMUITipDialogUtil.showOnlyWord(NewBaseDemoActivity.this, "left",
                        mCustomerBtnLayout);
            }

            @Override
            public void onMiddleClick() {
                QMUITipDialogUtil.showOnlyWord(NewBaseDemoActivity.this, "middle",
                        mCustomerBtnLayout);
            }

            @Override
            public void onRightClick() {
                QMUITipDialogUtil.showOnlyWord(NewBaseDemoActivity.this, "right",
                        mCustomerBtnLayout);
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }
}
