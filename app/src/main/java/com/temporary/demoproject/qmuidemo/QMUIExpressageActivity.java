package com.temporary.demoproject.qmuidemo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itheima.wheelpicker.WheelPicker;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.adapter.ExpressageAdapter;
import com.temporary.bean.ExpressageData;
import com.temporary.demoproject.BaseActivity;
import com.temporary.demoproject.R;
import com.temporary.demoproject.viewmodel.IExpressageView;
import com.temporary.presenter.ExpressagePresenter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QMUIExpressageActivity extends BaseActivity<IExpressageView, ExpressagePresenter>
        implements IExpressageView {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.expressage_type_textview)
    TextView mExpressageTypeTV;
    @BindView(R.id.expressage_num_edittext)
    EditText mExpressageNumET;

    @BindView(R.id.expressage_recyclerview)
    RecyclerView mExpressageRV;
    @BindView(R.id.inquire_expressage_button)
    QMUIRoundButton mInquireExpressageBtn;

    private PopupWindow mPopupWindow;
    private List<String> mExpressageTitleList;
    private String[] mExpressageValues;
    private int mExpressageTitleSelectPosition = 0;
    private ExpressageAdapter mExpressageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] expressageTitles = getResources().getStringArray(R.array.expressage_titles);
        mExpressageTitleList = Arrays.asList(expressageTitles);

        mExpressageValues = getResources().getStringArray(R.array.expressage_values);
    }

    @OnClick({R.id.expressage_type_textview, R.id.inquire_expressage_button})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.expressage_type_textview: {// 选择快递公司
                initExpressagePopupWindow();
                break;
            }
            case R.id.inquire_expressage_button: {// 查询 http://www.kuaidi100.com/query?type=zhongtong&postid=486349079222
                mPresenter.inquireExpressage(mExpressageValues[mExpressageTitleSelectPosition],
                        mExpressageNumET.getText().toString());
                break;
            }
        }
    }

    private void initExpressagePopupWindow() {
        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        getWindow().setAttributes(layoutParams);

        View view = LayoutInflater.from(this).inflate(R.layout.custom_expressage_popup_window,
                null, false);
        TextView expressageOkTV = (TextView) view.findViewById(R.id.expressage_ok_textview);
        expressageOkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExpressageTypeTV.setText(mExpressageTitleList.get(mExpressageTitleSelectPosition));
                mPopupWindow.dismiss();
            }
        });
        WheelPicker expressageWP = (WheelPicker) view.findViewById(R.id.expressage_wheelpicker);
        expressageWP.setData(mExpressageTitleList);
        expressageWP.setVisibleItemCount(5);
        expressageWP.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int i) {

            }

            @Override
            public void onWheelSelected(int i) {
                mExpressageTitleSelectPosition = i;
            }

            @Override
            public void onWheelScrollStateChanged(int i) {

            }
        });

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.showAtLocation(findViewById(R.id.expressage_layout),
                Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                getWindow().setAttributes(layoutParams);
            }
        });
    }

    @Override
    protected void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.expressage_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_qmui_expressage;
    }

    @Override
    protected ExpressagePresenter getPresenter() {
        return new ExpressagePresenter(this);
    }

    @Override
    public void initExpressageRecyclerView(List<ExpressageData> list) {
        mExpressageAdapter = new ExpressageAdapter(this, list);

        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mExpressageRV.setLayoutManager(manager);
        mExpressageRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mExpressageRV.setItemAnimator(new DefaultItemAnimator());
        mExpressageRV.setAdapter(mExpressageAdapter);
    }
}
