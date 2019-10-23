package com.temporary.demoproject.qmuidemo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itheima.wheelpicker.WheelPicker;
import com.itheima.wheelpicker.widgets.WheelDatePicker;
import com.itheima.wheelpicker.widgets.WheelDayPicker;
import com.itheima.wheelpicker.widgets.WheelMonthPicker;
import com.itheima.wheelpicker.widgets.WheelYearPicker;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopupWindowActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.date_button)
    QMUIRoundButton mDateBtn;
    @BindView(R.id.city_button)
    QMUIRoundButton mCityBtn;

    TextView mCancelTV;
    TextView mOkTV;
    WheelYearPicker mWheelYearPicker;
    WheelMonthPicker mWheelMonthPicker;
    WheelDayPicker mWheelDayPicker;
    WheelPicker mHourWheelPicker;
    WheelPicker mMinWheelPicker;
    WheelPicker mSecWheelPicker;
    @BindView(R.id.evaluation_button)
    QMUIRoundButton mEvaluationBtn;
    @BindView(R.id.content_view)
    LinearLayout mContentView;
    private PopupWindow mPopupWindow;

    private WheelPicker mWheelCityPicker;

    private RatingBar mRatingBar;

    WheelDatePicker mWheelDatePicker;
    WheelPicker mWheelStringPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        ButterKnife.bind(this);

        initTop();
    }

    private void initTop() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.popup_window_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.date_button, R.id.city_button, R.id.evaluation_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.date_button:// 日期选择器
                showDatePopupWindow();
                break;
            case R.id.city_button:// 城市选择器
                showCityPopupWindow();
                break;
            case R.id.evaluation_button: {// 评价
                showEvalutionPopupWindow();
                break;
            }
        }
    }

    private void showEvalutionPopupWindow() {
        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        getWindow().setAttributes(layoutParams);

        View view = LayoutInflater.from(this).inflate(R.layout.custom_popup_window_evaluation,
                null, false);
        mOkTV = (TextView) view.findViewById(R.id.ok_textview);
        mCancelTV = (TextView) view.findViewById(R.id.cancel_textview);
        mRatingBar = (RatingBar) view.findViewById(R.id.evaluation_ratingbar);

        mOkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("wyy", "PopupWindowActivity onClick " + mRatingBar.getRating());
                mEvaluationBtn.setText(String.valueOf(mRatingBar.getRating()));
                mPopupWindow.dismiss();
            }
        });
        mCancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.showAtLocation(findViewById(R.id.activity_popup_layout),
                Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                getWindow().setAttributes(layoutParams);
            }
        });
    }

    private void showCityPopupWindow() {
        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        getWindow().setAttributes(layoutParams);

        final List<String> citys = Arrays.asList(getResources().getStringArray(R.array.popupwindow_citys));

        View view = LayoutInflater.from(this).inflate(R.layout.custom_popup_window_city,
                null, false);
        mOkTV = (TextView) view.findViewById(R.id.ok_textview);
        mCancelTV = (TextView) view.findViewById(R.id.cancel_textview);
        mWheelCityPicker = (WheelPicker) view.findViewById(R.id.wheel_city_picker);
        mWheelCityPicker.setData(citys);

        mCancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mOkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCityBtn.setText(citys.get(mWheelCityPicker.getCurrentItemPosition()));
                mPopupWindow.dismiss();
            }
        });

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);/*QMUIDisplayHelper.dp2px(this, 500)*/
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.showAtLocation(findViewById(R.id.activity_popup_layout),
                Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                getWindow().setAttributes(layoutParams);
            }
        });
    }

    private void showDatePopupWindow() {
        final WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.5f;
        getWindow().setAttributes(layoutParams);

        View view = LayoutInflater.from(this).inflate(R.layout.custom_popup_window_date,
                null, false);
        mCancelTV = (TextView) view.findViewById(R.id.cancel_textview);
        mOkTV = (TextView) view.findViewById(R.id.ok_textview);
        mWheelYearPicker = (WheelYearPicker) view.findViewById(R.id.wheel_year_picker);
        mWheelMonthPicker = (WheelMonthPicker) view.findViewById(R.id.wheel_month_picker);
        mWheelDayPicker = (WheelDayPicker) view.findViewById(R.id.wheel_day_picker);

        mWheelYearPicker.setVisibleItemCount(5);
        mWheelMonthPicker.setVisibleItemCount(5);
        mWheelDayPicker.setVisibleItemCount(5);

        mHourWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_hour_picker);
        mMinWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_min_picker);
        mSecWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_sec_picker);

        String[] hourArray = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        final List<String> hourList = Arrays.asList(hourArray);
        List<String> minOrSecList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minOrSecList.add("0" + i);
            } else {
                minOrSecList.add(String.valueOf(i));
            }
        }
        mHourWheelPicker.setData(hourList);
        mMinWheelPicker.setData(minOrSecList);
        mSecWheelPicker.setData(minOrSecList);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date date = new Date(System.currentTimeMillis());
        String s = simpleDateFormat.format(date);
        mHourWheelPicker.setSelectedItemPosition(Integer.valueOf(s));

        mHourWheelPicker.setVisibleItemCount(5);
        mMinWheelPicker.setVisibleItemCount(5);
        mSecWheelPicker.setVisibleItemCount(5);

        simpleDateFormat = new SimpleDateFormat("mm");
        s = simpleDateFormat.format(date);
        mMinWheelPicker.setSelectedItemPosition(Integer.valueOf(s));

        simpleDateFormat = new SimpleDateFormat("ss");
        s = simpleDateFormat.format(date);
        mSecWheelPicker.setSelectedItemPosition(Integer.valueOf(s));

        mWheelYearPicker.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int i) {

            }

            @Override
            public void onWheelSelected(int i) {
                mWheelDayPicker.setYear(i + 1000);
            }

            @Override
            public void onWheelScrollStateChanged(int i) {

            }
        });
        mWheelMonthPicker.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolled(int i) {

            }

            @Override
            public void onWheelSelected(int i) {
                mWheelDayPicker.setMonth(i + 1);
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
        mPopupWindow.showAtLocation(findViewById(R.id.activity_popup_layout),
                Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                layoutParams.alpha = 1.0f;
                getWindow().setAttributes(layoutParams);
            }
        });
        mCancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mOkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = String.format(getResources().getString(R.string.popup_window_date_result_text),
                        mWheelYearPicker.getCurrentYear(), mWheelMonthPicker.getCurrentMonth(),
                        mWheelDayPicker.getCurrentDay());
                mDateBtn.setText(result);
                mPopupWindow.dismiss();
            }
        });
    }
}
