package com.temporary.demoproject.qmuidemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.temporary.demoproject.R;
import com.temporary.demoproject.fragment.OneFragment;
import com.temporary.demoproject.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUITabFixedActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.tab_segment)
    QMUITabSegment mTabSegment;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();
    private int[] mViews = {R.layout.qmui_tab_view, R.layout.qmui_tab_view};

    private FragmentPagerAdapter mFragmentPagerAdapter;

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mViews.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            View view = LayoutInflater.from(getApplicationContext()).inflate(mViews[position], null);
            /*ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);*/
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    };

    public static Intent getInstanceIntent(Context context) {
        Intent intent = new Intent(context, QMUITabFixedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuitab_fixed);
        ButterKnife.bind(this);

        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        mFragments.add(oneFragment);
        mFragments.add(twoFragment);
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        initTopbar();
        initTabSegment();
    }

    private void initTabSegment() {
        // mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setCurrentItem(0, false);
        mTabSegment.addTab(new QMUITabSegment.Tab("tab1"));
        mTabSegment.addTab(new QMUITabSegment.Tab("tab2"));
        mTabSegment.setupWithViewPager(mViewPager, false);
        mTabSegment.setMode(QMUITabSegment.MODE_FIXED);
        mTabSegment.setHasIndicator(true);
        mTabSegment.setIndicatorWidthAdjustContent(false);
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {
                mTabSegment.hideSignCountView(index);
            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_fixed_width_text));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTopbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id.topbar_right_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String[] items = getResources().getStringArray(R.array.qmui_fixed_bottom);
                        final QMUIBottomSheet.BottomListSheetBuilder builder =
                                new QMUIBottomSheet.BottomListSheetBuilder(QMUITabFixedActivity.this);
                        for (String s : items) {
                            builder.addItem(s);
                        }
                        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                changeTap(position);
                                dialog.dismiss();
                            }
                        });
                        builder.build().show();
                    }
                });
    }

    private void changeTap(int position) {
        switch (position) {
            case 0: {// 简单文字
                mTabSegment.reset();
                mTabSegment.addTab(new QMUITabSegment.Tab("tab1"));
                mTabSegment.addTab(new QMUITabSegment.Tab("tab2"));
                mTabSegment.setHasIndicator(false);

                break;
            }
            case 1: {// 文字 + 底部indicator
                mTabSegment.reset();
                mTabSegment.addTab(new QMUITabSegment.Tab("tab1"));
                mTabSegment.addTab(new QMUITabSegment.Tab("tab2"));
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                mTabSegment.setIndicatorWidthAdjustContent(true);
                break;
            }
            case 2: {// 文字 + 顶部indicator
                mTabSegment.reset();
                mTabSegment.addTab(new QMUITabSegment.Tab("tab1"));
                mTabSegment.addTab(new QMUITabSegment.Tab("tab2"));
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(true);
                mTabSegment.setIndicatorWidthAdjustContent(true);
                break;
            }
            case 3: {// 文字 + indicator长度不要跟随内容长度
                mTabSegment.reset();
                mTabSegment.addTab(new QMUITabSegment.Tab("tab1"));
                mTabSegment.addTab(new QMUITabSegment.Tab("tab2"));
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                mTabSegment.setIndicatorWidthAdjustContent(false);
                break;
            }
            case 4: {// 文字 + icon(支持四个方向) + 自动着色选中态
                mTabSegment.reset();
                QMUITabSegment.Tab componentTab = new QMUITabSegment.Tab(getResources().getDrawable(R.mipmap.icon_tabbar_component),
                        null, "component", true);
                QMUITabSegment.Tab labTab = new QMUITabSegment.Tab(getResources().getDrawable(R.mipmap.icon_tabbar_lab),
                        null, "lab", true);
                mTabSegment.addTab(componentTab);
                mTabSegment.addTab(labTab);
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                break;
            }
            case 5: {// 显示红点
                QMUITabSegment.Tab tab = mTabSegment.getTab(0);
                tab.setSignCountMargin(0, 0);
                tab.showSignCountView(this, 2);
                break;
            }
            case 6: {// 选中态更换 icon
                mTabSegment.reset();
                QMUITabSegment.Tab componentTab = new QMUITabSegment.Tab(getResources().getDrawable(R.mipmap.icon_tabbar_component),
                        getResources().getDrawable(R.mipmap.icon_tabbar_component_selected),
                        "component", false);
                QMUITabSegment.Tab labTab = new QMUITabSegment.Tab(getResources().getDrawable(R.mipmap.icon_tabbar_lab),
                        getResources().getDrawable(R.mipmap.icon_tabbar_lab_selected),
                        "component", false);
                mTabSegment.addTab(componentTab);
                mTabSegment.addTab(labTab);
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
            }
            case 7: {// 不同 item，不同文字(icon)颜色
                mTabSegment.reset();
                QMUITabSegment.Tab componentTab = new QMUITabSegment.Tab(
                        getResources().getDrawable(R.mipmap.icon_tabbar_component),
                        null, "component", true);
                componentTab.setTextColor(getResources().getColor(android.R.color.darker_gray),
                        getResources().getColor(R.color.colorPrimary));
                QMUITabSegment.Tab labTab = new QMUITabSegment.Tab(
                        getResources().getDrawable(R.mipmap.icon_tabbar_lab),
                        null, "component", true);
                labTab.setTextColor(getResources().getColor(android.R.color.darker_gray),
                        getResources().getColor(R.color.colorPrimary));
                mTabSegment.addTab(componentTab);
                mTabSegment.addTab(labTab);
                mTabSegment.setHasIndicator(true);
                mTabSegment.setIndicatorPosition(false);
                break;
            }
            case 8: {// 根据 index 更新 tab 文案
                mTabSegment.updateTabText(0, "new_component");
                break;
            }
            case 9: {// 根据 index 完全替换 tab
                QMUITabSegment.Tab newComponentTab = new QMUITabSegment.Tab(
                        getResources().getDrawable(R.mipmap.icon_tabbar_component_selected), null,
                        "new_component", true);
                mTabSegment.replaceTab(1, newComponentTab);
                break;
            }
        }
        mTabSegment.notifyDataChanged();
    }
}
