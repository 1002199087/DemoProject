package com.temporary.demoproject.qmuidemo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.adapter.PopupSpinnerAdapter;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopupSpinnerActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.gray_layout)
    View mGrayLayout;

    private TextView mTitleTV;

    private List<String> mStateList;
    private PopupSpinnerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_spinner);
        ButterKnife.bind(this);

        initTopbar();

        mStateList = new ArrayList<>();
        String[] stateArray = {"全部", "待处理", "已失效", "已派单", "检修中", "待分析", "已完结"};
        mStateList = Arrays.asList(stateArray);
        mAdapter = new PopupSpinnerAdapter(this, mStateList);
        mAdapter.setPopupSpinnerAdapterListener(new PopupSpinnerAdapter.PopupSpinnerAdapterListener() {
            @Override
            public void onClick(int position) {
                mTitleTV.setText(mStateList.get(position));
                mPopupWindow.dismiss();
            }
        });

        View view = LayoutInflater.from(this).inflate(R.layout.popup_custom_view,
                null, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.popup_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mPopupWindow = new PopupWindow(view, RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.popup_spinner_anim);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                /*WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1.0f;
                getWindow().setAttributes(layoutParams);*/
                mGrayLayout.setVisibility(View.GONE);
            }
        });
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View view = LayoutInflater.from(this).inflate(R.layout.popup_center_view,
                null, false);
        mTitleTV = (TextView) view.findViewById(R.id.center_textview);
        mTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.showAsDropDown(mTopbar);

                /*WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 0.5f;
                getWindow().setAttributes(layoutParams);*/

                mGrayLayout.setVisibility(View.VISIBLE);
            }
        });
        mTopbar.setCenterView(view);
    }
}
