package com.temporary.demoproject.qmuidemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.custom.CustomDialog;
import com.temporary.demoproject.NewBaseActivity;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustDialogActivity extends NewBaseActivity {

    @BindView(R.id.one_dialog_button)
    QMUIRoundButton mOneDialogBtn;

    public static Intent getIntent(Context context) {
        return new Intent(context, CustDialogActivity.class);
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.custom_dialog_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_cust_dialog;
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

    @OnClick(R.id.one_dialog_button)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_dialog_button: {// 带确定、取消
                final CustomDialog dialog = new CustomDialog(this);
                dialog.setCustomTitle("提示")
                        .setCustomMsg("这是带确定、取消的dialog测试message")
                        .setOnCustomDialogCallback(new CustomDialog.CustomDialogCallback() {
                            @Override
                            public void onConfirmListener() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelListener() {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
                break;
            }
        }
    }
}