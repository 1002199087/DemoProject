package com.temporary.demoproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends NewBaseActivity {

    @BindView(R.id.pic_banner)
    Banner mPicBanner;

    private List<String> mImagePaths;
    private List<String> mImageTitles;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, BannerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.banner_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView() {
        //设置图片资源:url或本地资源
        mImagePaths = new ArrayList<>();
        mImagePaths.add("http://img.zcool.cn/community/0166c756e1427432f875520f7cc838.jpg");
        mImagePaths.add("http://img.zcool.cn/community/018fdb56e1428632f875520f7b67cb.jpg");
        mImagePaths.add("http://img.zcool.cn/community/01c8dc56e1428e6ac72531cbaa5f2c.jpg");
        mImagePaths.add("http://img.zcool.cn/community/01fda356640b706ac725b2c8b99b08.jpg");
        mImagePaths.add("http://img.zcool.cn/community/01fd2756e142716ac72531cbf8bbbf.jpg");
        mImagePaths.add("http://img.zcool.cn/community/0114a856640b6d32f87545731c076a.jpg");
        //设置图片标题:自动对应
        mImageTitles = new ArrayList<>();
        mImageTitles.add("十大星级品牌联盟，全场2折起");
        mImageTitles.add("嗨购5折不要停");
        mImageTitles.add("双12趁现在");
        mImageTitles.add("嗨购5折不要停，12.12趁现在");
        mImageTitles.add("实打实大优惠");
        mImageTitles.add("买到就是赚到");

        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR   显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        //设置banner样式
        mPicBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        mPicBanner.setImageLoader(new GlideImageLoader());
        //设置标题集合（当banner样式有显示title时）
        mPicBanner.setBannerTitles(mImageTitles);
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT   指示器居左
        //Banner.CENTER 指示器居中
        //Banner.RIGHT  指示器居右
        mPicBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置是否允许手动滑动轮播图
        mPicBanner.setViewPagerIsScroll(true);
        //设置是否自动轮播（不设置则默认自动）
        mPicBanner.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        mPicBanner.setDelayTime(3000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        mPicBanner.setImages(mImagePaths)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(BannerActivity.this, "你点了第" + (position + 1) + "张轮播图",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    @Override
    public void init() {

    }

    /**
     * 网络加载图片
     * 使用了Glide图片加载框架
     */
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context)
                    .load((String) path)
                    .into(imageView);
        }

    }

    @Override
    public int getTopbarMode() {
        return NO_BAR;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
