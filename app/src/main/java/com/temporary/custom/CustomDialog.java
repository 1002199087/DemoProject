package com.temporary.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.temporary.demoproject.R;

/**
 * theme:
 * author：wyy
 */
public class CustomDialog extends Dialog {
    TextView mTitleTv;
    TextView mMsgTv;
    TextView mConfirmTv;
    TextView mCancelTv;

    private Context mContext;

    private String mTitle;
    private String mMsg;

    private CustomDialogCallback mCallback;

    public CustomDialog(@NonNull Context context) {
        super(context, R.style.cust_dialog);
        mContext = context;
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable,
                           @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.confirm_tv: {// 确定
                mCallback.onConfirmListener();
                break;
            }
            case R.id.cancel_tv: {// 取消
                mCallback.onCancelListener();
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        initView();
    }

    private void initView() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mMsgTv = (TextView) findViewById(R.id.msg_tv);
        mConfirmTv = (TextView) findViewById(R.id.confirm_tv);
        mCancelTv = (TextView) findViewById(R.id.cancel_tv);

        mConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClicked(view);
            }
        });
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClicked(view);
            }
        });
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    public CustomDialog setCustomTitle(String title) {
        mTitle = title;
        return this;
    }

    public CustomDialog setCustomTitle(int res) {
        mTitle = mContext.getString(res);
        return this;
    }

    public String getCustomTitle() {
        return mTitleTv.getText().toString();
    }

    public CustomDialog setCustomMsg(String msg) {
        mMsg = msg;
        return this;
    }

    public CustomDialog setCustomMsg(int res) {
        mMsg = mContext.getString(res);
        return this;
    }

    private void refreshView() {
        mTitleTv.setText(mTitle);
        mMsgTv.setText(mMsg);

        Window window = this.getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (QMUIDisplayHelper.getScreenWidth(mContext) * 0.8);
        window.setAttributes(params);
    }

    public void setOnCustomDialogCallback(CustomDialogCallback callback) {
        mCallback = callback;
    }

    public interface CustomDialogCallback {
        void onConfirmListener();

        void onCancelListener();
    }
}
