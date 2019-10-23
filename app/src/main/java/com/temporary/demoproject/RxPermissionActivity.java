package com.temporary.demoproject;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.temporary.demoproject.databinding.ActivityRxPermissionBinding;
import com.temporary.util.QMUITipDialogUtil;

import java.util.function.Consumer;

import rx.Observable;
import rx.Observer;

public class RxPermissionActivity extends SimpleBaseActivity<ActivityRxPermissionBinding> {
    @Override
    protected int getContentView() {
        return R.layout.activity_rx_permission;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.rxpermission_text));
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

    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.rxpermission_button: {
                new RxPermissions(this).request(Manifest.permission.CHANGE_WIFI_STATE).subscribe
                        (new io
                                .reactivex.functions.Consumer<Boolean>() {

                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                QMUITipDialogUtil.showOnlyWord(RxPermissionActivity.this,
                                        "权限获取flag: " + aBoolean, view);
                            }

                        });

                break;
            }
        }
    }
}
