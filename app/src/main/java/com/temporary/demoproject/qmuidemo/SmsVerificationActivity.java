package com.temporary.demoproject.qmuidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SmsVerificationActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.phone_num_edittext)
    EditText mPhoneNumET;
    @BindView(R.id.phone_verification_edittext)
    EditText mPhoneVerificationET;
    @BindView(R.id.get_verification_button)
    Button mGetVerificationBtn;
    @BindView(R.id.send_button)
    Button mSendBtn;

    private String mPhoneNum;
    private String mVerificationNum;
    private EventHandler mEventHandler;
    private MySMSHandler mSMSHandler;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        ButterKnife.bind(this);

        initTopbar();

        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long l) {
                mGetVerificationBtn.setText(
                        String.format(getResources().getString(R.string.qmui_sms_verification_num_count_timer),
                                l / 1000));
            }

            @Override
            public void onFinish() {
                mGetVerificationBtn.setClickable(true);
                mGetVerificationBtn.setText(
                        getResources().getString(R.string.qmui_sms_verification_get_phone_verification_text));
            }
        };
        MobSDK.init(this);
        // SMSSDK.getSupportedCountries();// 获取短信目前支持的国家列表，在监听中返回
        mSMSHandler = new MySMSHandler(this);

        mEventHandler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object o) {
                super.afterEvent(event, result, o);
                Message message = mSMSHandler.obtainMessage();
                message.arg1 = event;
                message.arg2 = result;
                message.obj = o;
                message.sendToTarget();
            }

        };
        SMSSDK.registerEventHandler(mEventHandler);
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_sms_verification_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.get_verification_button, R.id.send_button})
    public void onViewClicked(View view) {
        mPhoneNum = mPhoneNumET.getText().toString();
        mVerificationNum = mPhoneVerificationET.getText().toString();
        switch (view.getId()) {
            case R.id.get_verification_button: {
                if (mPhoneNum != null && !mPhoneNum.equals("")) {
                    SMSSDK.getVerificationCode("86", mPhoneNum);
                    mGetVerificationBtn.setClickable(false);
                    mCountDownTimer.start();
                } else {
                    Toast.makeText(this,
                            getResources().getString(R.string.qmui_sms_phone_num_null_toast),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.send_button: {
                if (mPhoneNum != null && !mPhoneNum.equals("")
                        && mVerificationNum != null && !mVerificationNum.equals("")) {
                    SMSSDK.submitVerificationCode("86", mPhoneNum, mVerificationNum);
                } else {
                    Toast.makeText(this,
                            getResources().getString(R.string.qmui_sms_phone_and_verification_num_null_toast),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private class MySMSHandler extends Handler {
        private WeakReference<Activity> mWeakReference;

        public MySMSHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mWeakReference.get() != null) {
                int event = msg.arg1;
                int result = msg.arg2;
                if (result == SMSSDK.RESULT_COMPLETE) {//  回调完成
                    switch (event) {
                        case SMSSDK.EVENT_GET_VERIFICATION_CODE: {// 获取验证码成功
                            Toast.makeText(SmsVerificationActivity.this,
                                    "获取验证码成功", Toast.LENGTH_SHORT).show();
                            mCountDownTimer.onFinish();
                            break;
                        }
                        case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE: {// 提交验证码成功
                            Toast.makeText(SmsVerificationActivity.this,
                                    "提交验证码成功", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES: {// 返回支持发送验证码的国家列表
                            break;
                        }
                    }
                }
            }
        }
    }
}
