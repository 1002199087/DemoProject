package com.temporary.demoproject.qmuidemo;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.itheima.wheelpicker.WheelPicker;
import com.itheima.wheelpicker.widgets.WheelDatePicker;
import com.itheima.wheelpicker.widgets.WheelDayPicker;
import com.itheima.wheelpicker.widgets.WheelMonthPicker;
import com.itheima.wheelpicker.widgets.WheelYearPicker;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.AlarmManagerActivity;
import com.temporary.demoproject.AlipayActivity;
import com.temporary.demoproject.ArcBgActivity;
import com.temporary.demoproject.BannerActivity;
import com.temporary.demoproject.BindServiceActivity;
import com.temporary.demoproject.CalendarViewActivity;
import com.temporary.demoproject.DalvikOrARTActivity;
import com.temporary.demoproject.DataBindingActivity;
import com.temporary.demoproject.EventBusMainActivity;
import com.temporary.demoproject.GlideActivity;
import com.temporary.demoproject.KotlinActivity;
import com.temporary.demoproject.LaunchModeActivity;
import com.temporary.demoproject.LoadingLayoutActivity;
import com.temporary.demoproject.LogAddActivity;
import com.temporary.demoproject.MPandroidChartActivity;
import com.temporary.demoproject.MVPAndRetrofitAndRxJavaActivity;
import com.temporary.demoproject.MVVMModeActivity;
import com.temporary.demoproject.ObserveActivity;
import com.temporary.demoproject.R;
import com.temporary.demoproject.RadioGroupActivity;
import com.temporary.demoproject.RxPermissionActivity;
import com.temporary.demoproject.SignatureActivity;
import com.temporary.demoproject.SpringBootRequestActivity;
import com.temporary.demoproject.WebsiteActivity;
import com.temporary.test.JsonActivity;
import com.vise.log.ViseLog;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QmuiMainActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.button_demo)
    QMUIRoundButton mButtonDemoBtn;
    @BindView(R.id.dialog_button)
    QMUIRoundButton mDialogButton;
    @BindView(R.id.empty_view_button)
    QMUIRoundButton mEmptyViewBtn;
    @BindView(R.id.qmui_tab_button)
    QMUIRoundButton mTabButton;
    @BindView(R.id.qmui_grouplist_button)
    QMUIRoundButton mQmuiGrouplistBtn;
    @BindView(R.id.qmui_tipdialog_button)
    QMUIRoundButton mQmuiTipdialogBtn;
    @BindView(R.id.qmui_radiusimage_button)
    QMUIRoundButton mQmuiRadiusimageBtn;
    @BindView(R.id.qmui_pullrefresh_button)
    QMUIRoundButton mQmuiPullrefreshBtn;
    @BindView(R.id.qmui_popup_button)
    QMUIRoundButton mQmuiPopupBtn;
    @BindView(R.id.qmui_collapsing_topbar_button)
    QMUIRoundButton mQmuiCollapsingTopbarBtn;
    @BindView(R.id.qmui_viewhelper_button)
    QMUIRoundButton mQmuiViewhelperBtn;
    @BindView(R.id.qmui_animationlist_button)
    QMUIRoundButton mQmuiAnimationlistBtn;
    @BindView(R.id.qmui_snaphelper_button)
    QMUIRoundButton mQmuiSnaphelperBtn;
    @BindView(R.id.search_layout_button)
    QMUIRoundButton mSearchLayoutBtn;
    @BindView(R.id.fragment_button)
    Button fragmentButton;
    @BindView(R.id.fragment_button_layout)
    LinearLayout fragmentButtonLayout;
    @BindView(R.id.popup_window_button)
    QMUIRoundButton mPopupWindowBtn;
    @BindView(R.id.select_photp_button)
    QMUIRoundButton mSelectPhotpBtn;
    WheelYearPicker mWheelYearPicker;
    WheelMonthPicker mWheelMonthPicker;
    WheelDayPicker mWheelDayPicker;
    WheelDatePicker mWheelDatePicker;
    @BindView(R.id.qmui_sms_verification_button)
    QMUIRoundButton mSmsVerificationBtn;
    @BindView(R.id.qmui_baidumap_location_button)
    QMUIRoundButton mBaidumapLocationBtn;
    @BindView(R.id.swipe_menu_layout_button)
    QMUIRoundButton mSwipeMenuLayoutBtn;
    @BindView(R.id.webview_layout_button)
    QMUIRoundButton mWebviewLayoutBtn;
    @BindView(R.id.expand_layout_button)
    QMUIRoundButton mExpandLayoutBtn;
    @BindView(R.id.popup_spinner_button)
    QMUIRoundButton mPopupSpinnerBtn;
    @BindView(R.id.qmui_progressbar_button)
    QMUIRoundButton mQmuiProgressbarBtn;
    @BindView(R.id.mp_chart_button)
    QMUIRoundButton mMpChartBtn;
    @BindView(R.id.radio_group_button)
    QMUIRoundButton mRadioGroupBtn;
    @BindView(R.id.retrofit_button)
    QMUIRoundButton mRetrofitBtn;
    @BindView(R.id.expressage_button)
    QMUIRoundButton mExpressageBtn;
    @BindView(R.id.ExpandableListView_button)
    QMUIRoundButton mExpandableListViewBtn;
    @BindView(R.id.fresco_button)
    QMUIRoundButton mFrescoBtn;
    @BindView(R.id.powercloud_button)
    QMUIRoundButton mPowercloudBtn;
    @BindView(R.id.calendarview_button)
    QMUIRoundButton mCalendarViewBtn;
    @BindView(R.id.signature_button)
    QMUIRoundButton mSignatureBtn;
    @BindView(R.id.observe_button)
    QMUIRoundButton mObserveBtn;
    @BindView(R.id.databinding_button)
    QMUIRoundButton mDatabindingBtn;
    @BindView(R.id.eventbus_button)
    QMUIRoundButton mEventbusBtn;
    @BindView(R.id.launchmode_button)
    QMUIRoundButton mLaunchModeBtn;
    @BindView(R.id.mvp_retrofit_rxjava_button)
    QMUIRoundButton mMvpRetrofitRxjavaBtn;
    @BindView(R.id.glide_button)
    QMUIRoundButton mGlideBtn;
    @BindView(R.id.art_button)
    QMUIRoundButton mArtBtn;
    @BindView(R.id.service_button)
    QMUIRoundButton mServiceBtn;
    @BindView(R.id.alipay_button)
    QMUIRoundButton mAlipayBtn;
    @BindView(R.id.qq_login_button)
    QMUIRoundButton mQqLoginBtn;
    @BindView(R.id.greendao_button)
    QMUIRoundButton mGreendaoBtn;
    @BindView(R.id.rxpermission_button)
    QMUIRoundButton mRxpermissionBtn;
    @BindView(R.id.new_baseactivity_button)
    QMUIRoundButton mNewBaseBtn;
    @BindView(R.id.website_button)
    QMUIRoundButton mWebsiteBtn;
    @BindView(R.id.banner_button)
    QMUIRoundButton mBannerBtn;
    @BindView(R.id.arc_bg_button)
    QMUIRoundButton mArcBgBtn;
    @BindView(R.id.springBoot_request_button)
    QMUIRoundButton mSpringBootBtn;
    @BindView(R.id.mvvm_button)
    QMUIRoundButton mMvvmBtn;
    @BindView(R.id.kotlin_button)
    QMUIRoundButton mKotlinBtn;
    @BindView(R.id.recyler_log_button)
    QMUIRoundButton mRecylerLogBtn;
    @BindView(R.id.alarm_manager_button)
    QMUIRoundButton mAlarmManagerBtn;
    private WheelPicker mWheelPicker;

    private PopupWindow mPopupWindow;

    private final int SEARCH_RESQUEST_CODE = 0;
    private final int QUERY_PHOTO_RESQUEST_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmui_main);

        ButterKnife.bind(this);

        checkPermission();
        initQmuiTop();
    }

    /**
     * 沉浸式状态栏
     */
    private void initQmuiTop() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle("状态栏");
    }

    private void demoCode() {
        String s = "111a";
        int i = Integer.valueOf(s);
    }

    @OnClick({R.id.button_demo, R.id.dialog_button, R.id.empty_view_button, R.id.qmui_tab_button,
            R.id.qmui_grouplist_button, R.id.qmui_tipdialog_button, R.id.qmui_radiusimage_button,
            R.id.qmui_pullrefresh_button, R.id.qmui_popup_button, R.id
            .qmui_collapsing_topbar_button,
            R.id.qmui_viewhelper_button, R.id.qmui_animationlist_button, R.id
            .qmui_snaphelper_button,
            R.id.search_layout_button, R.id.popup_window_button, R.id.select_photp_button,
            R.id.qmui_sms_verification_button, R.id.qmui_baidumap_location_button,
            R.id.swipe_menu_layout_button, R.id.webview_layout_button, R.id.expand_layout_button,
            R.id.popup_spinner_button, R.id.qmui_progressbar_button, R.id.mp_chart_button,
            R.id.radio_group_button, R.id.retrofit_button, R.id.expressage_button,
            R.id.ExpandableListView_button, R.id.fresco_button, R.id.powercloud_button,
            R.id.calendarview_button, R.id.signature_button, R.id.observe_button, R.id
            .databinding_button, R.id.eventbus_button, R.id.launchmode_button, R.id
            .mvp_retrofit_rxjava_button, R.id.glide_button, R.id.art_button, R.id.service_button,
            R.id.alipay_button, R.id.qq_login_button, R.id.greendao_button, R.id
            .rxpermission_button, R.id.new_baseactivity_button, R.id.website_button,
            R.id.banner_button, R.id.arc_bg_button, R.id.springBoot_request_button,
            R.id.mvvm_button, R.id.kotlin_button, R.id.recyler_log_button,
            R.id.alarm_manager_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alarm_manager_button: {// AlarmManager 定时提醒
                startActivity(AlarmManagerActivity.getIntent(this));
                break;
            }
            case R.id.recyler_log_button: {// recycler log 组件
                startActivity(LogAddActivity.getIntent(this));
                break;
            }
            case R.id.kotlin_button: {// kotlin 开发
                startActivity(KotlinActivity.Companion.getIntent(this));
                break;
            }
            case R.id.mvvm_button: {// MVVM 模式
                startActivity(MVVMModeActivity.getIntent(this));
                break;
            }
            case R.id.springBoot_request_button: {// SpringBoot网络请求
                startActivity(SpringBootRequestActivity.getIntent(this));
                break;
            }
            case R.id.arc_bg_button: {// 弧形背景
                startActivity(ArcBgActivity.getIntent(this));
                break;
            }
            case R.id.banner_button: {// banner图片轮播
                startActivity(BannerActivity.getIntent(this));
                break;
            }
            case R.id.button_demo: {// 圆角按键
                ViseLog.d("圆角按键");
                Intent intent = new Intent(this, QMUIRoundButtonActivity.class);
                startActivity(intent);
                //QMUIStatusBarHelper.setStatusBarDarkMode(QmuiMainActivity.this);
                //queryPhotos();
                break;
            }
            case R.id.dialog_button: {// 弹出框
                Intent intent = new Intent(this, QMUIDialogActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.empty_view_button: {// 空界面组件
                Intent intent = new Intent(this, QMUIEmptyViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_tab_button: {// tab 组件
                Intent intent = new Intent(this, QMUITabActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_grouplist_button: {// grouplist 组件
                Intent intent = new Intent(this, QMUIGroupListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_tipdialog_button: {// tipdialg 组件
                Intent intent = new Intent(this, QMUITipDialogActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_radiusimage_button: {// 图片剪裁 组件
                Intent intent = new Intent(this, QMUIRadiusImageViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_pullrefresh_button: {// 下拉刷新 组件
                Intent intent = new Intent(this, QMUIPullRefreshLayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_popup_button: {// 浮动弹出框组件
                Intent intent = new Intent(this, QMUIPopupActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_collapsing_topbar_button: {// 滚动topbar控件
                Intent intent = new Intent(this, QMUICollapsingTopBarLayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_viewhelper_button: {// 动画空间
                Intent intent = new Intent(this, QMUIViewHelperActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_animationlist_button: {// 带动画列表控件
                Intent intent = new Intent(this, QMUIAnimationListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_snaphelper_button: {// 滚动页面
                Intent intent = new Intent(this, QMUISnapHelperActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.search_layout_button: {// 搜索框
                Intent intent = new Intent(this, SearchLayoutActivity.class);
                startActivityForResult(intent, SEARCH_RESQUEST_CODE);
                break;
            }
            case R.id.popup_window_button: {// 底部弹出框
                //initPopupWindow();
                Intent intent = new Intent(this, PopupWindowActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_sms_verification_button: {// 短信验证
                Intent intent = new Intent(this, SmsVerificationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.select_photp_button: {// 调用系统相册选择图片上传
                Intent intent = new Intent(this, QueryPhotoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_baidumap_location_button: {// 百度地图定位
                Intent intent = new Intent(this, BaiduMapLoactionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.swipe_menu_layout_button: {// 列表滑动删除控件
                Intent intent = new Intent(this, SwipeMenuLayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.webview_layout_button: {// 网页内容显示控件
                Intent intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.expand_layout_button: {// expand_layout_button
                Intent intent = new Intent(this, ExpandViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.popup_spinner_button: {// popup下拉框
                Intent intent = new Intent(this, PopupSpinnerActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qmui_progressbar_button: {// qmui进度条
                Intent intent = new Intent(this, QMUIProgressBarActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.mp_chart_button: {// MpAndroidChart 图表
                Intent intent = new Intent(this, MPandroidChartActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.radio_group_button: {// RadioGroup 实现底部选项
                Intent intent = new Intent(this, RadioGroupActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.retrofit_button: {// 网络通信 retrofit
                Intent intent = new Intent(this, JsonActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.expressage_button: {// 快递查询
                Intent intent = new Intent(this, QMUIExpressageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ExpandableListView_button: {// 二级列表展开
                Intent intent = new Intent(this, ExpandableListViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.fresco_button: {// 加载网络图片_Fresco
                Intent intent = new Intent(this, FrescoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.powercloud_button: {// powercloud 界面
                break;
            }
            case R.id.calendarview_button: {// 日历框架_CalendarView
                Intent intent = new Intent(this, CalendarViewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.signature_button: {// 电子签名
                Intent intent = new Intent(this, SignatureActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.observe_button: {// 响应式编程
                Intent intent = new Intent(this, ObserveActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.databinding_button: {// databinding框架
                Intent intent = new Intent(this, DataBindingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.eventbus_button: {// EventBus框架
                Intent intent = new Intent(this, EventBusMainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.launchmode_button: {// launchmodel 模式
                Intent intent = new Intent(this, LaunchModeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.mvp_retrofit_rxjava_button: {// MVP+Retrofit+RxJava 模式
                Intent intent = new Intent(this, MVPAndRetrofitAndRxJavaActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.glide_button: {// glide 框架
                Intent intent = new Intent(this, GlideActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.art_button: {// 判断虚拟机是ART or Dalvik
                Intent intent = new Intent(this, DalvikOrARTActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.service_button: {// 启动服务的两种方式
                Intent intent = new Intent(this, BindServiceActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.alipay_button: {// 支付宝支付
                Intent intent = new Intent(this, AlipayActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.qq_login_button: {// QQ登陆

                break;
            }
            case R.id.greendao_button: {// GreenDao数据库
                break;
            }
            case R.id.rxpermission_button: {// RxPermission 动态权限框架
                Intent intent = new Intent(this, RxPermissionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.new_baseactivity_button: {// 新BaseActivity
                //startActivity(NewBaseDemoActivity.getIntent(this));

                //encryptByPublicKey();
                //startActivity(new Intent(this, LoadingViewActivity.class));

                startActivity(new Intent(this, LoadingLayoutActivity.class));

                //startActivity(new Intent(this, LoadingLibraryActivity.class));

                //startActivity(new Intent(this, OverlayActivity.class));
                break;
            }
            case R.id.website_button: {// 网站登陆
                startActivity(WebsiteActivity.getIntent(this));
                break;
            }
            default:
                break;
        }
    }

    private void encryptByPublicKey() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Log.e("wyy", "QmuiMainActivity encryptByPublicKey 111");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec("999".getBytes());
            Log.e("wyy", "QmuiMainActivity encryptByPublicKey 222");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Log.e("wyy", "QmuiMainActivity encryptByPublicKey 333");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("wyy", "QmuiMainActivity encryptByPublicKey " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            Log.e("wyy", "QmuiMainActivity encryptByPublicKey 222 " + e.getMessage());
        }

    }

    private void checkPermission() {
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .READ_EXTERNAL_STORAGE}, 0);
        }*/
    }

    private void initPopupWindow() {
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_popup_window_date,
                null, false);
        mWheelYearPicker = (WheelYearPicker) view.findViewById(R.id.wheel_year_picker);
        mWheelMonthPicker = (WheelMonthPicker) view.findViewById(R.id.wheel_month_picker);
        mWheelDayPicker = (WheelDayPicker) view.findViewById(R.id.wheel_day_picker);

        mWheelPicker = (WheelPicker) view.findViewById(R.id.wheel_hour_picker);
        mWheelDatePicker = (WheelDatePicker) view.findViewById(R.id.wheel_date_picker);

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

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("item " + i);
        }
        mWheelPicker.setData(list);

        mPopupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        mPopupWindow.showAtLocation(findViewById(R.id.relative_layout_main), Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
                Log.e("wyy", "QmuiMainActivity onDismiss " + mWheelPicker.getCurrentItemPosition());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SEARCH_RESQUEST_CODE: {
                if (resultCode == 2) {
                    String result = data.getStringExtra("search_input_result");
                    Toast.makeText(this, "result = " + result, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case QUERY_PHOTO_RESQUEST_CODE: {
                break;
            }
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
