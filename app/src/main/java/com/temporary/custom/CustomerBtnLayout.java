package com.temporary.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.temporary.demoproject.R;

public class CustomerBtnLayout extends FrameLayout {
    private Button mLeftBtn;
    private Button mMiddleBtn;
    private Button mRightBtn;

    public CustomerBtnLayout(@NonNull Context context) {
        super(context);
    }

    public CustomerBtnLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.customer_button_layout, this);
        mLeftBtn = view.findViewById(R.id.left_btn);
        mMiddleBtn = view.findViewById(R.id.middle_btn);
        mRightBtn = view.findViewById(R.id.right_btn);

        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onLeftClick();
            }
        });
        mMiddleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMiddleClick();
            }
        });
        mRightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRightClick();
            }
        });
    }

    public CustomerBtnLayout(@NonNull Context context, @Nullable AttributeSet attrs,
                             int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setCustomerBtnLayoutListener(CustomerBtnLayoutListener layoutListener) {
        this.mListener = layoutListener;
    }

    private CustomerBtnLayoutListener mListener;

    public interface CustomerBtnLayoutListener {
        void onLeftClick();

        void onMiddleClick();

        void onRightClick();
    }
}
