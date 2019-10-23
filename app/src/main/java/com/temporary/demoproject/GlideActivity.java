package com.temporary.demoproject;

import android.graphics.BlurMaskFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.temporary.custom.GlideBlurformation;
import com.temporary.demoproject.databinding.ActivityGlideBinding;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GlideActivity extends SimpleBaseActivity<ActivityGlideBinding> {
    private final String mPath = "http://ww3.sinaimg.cn/mw600/0073tLPGgy1fv6ijbjdznj30u00k0gpg.jpg";

    @Override
    protected int getContentView() {
        return R.layout.activity_glide;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.glide_title));
        mDataBinding.mQmuiTopbar.addLeftBackImageButton().setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDataBinding.mQmuiTopbar.addRightImageButton(R.mipmap.icon_topbar_overflow, R.id
                .topbar_right_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {// glide_test.jpg
                String[] values = getApplicationContext().getResources().getStringArray(R.array
                        .glide_values);
                QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet
                        .BottomListSheetBuilder(GlideActivity.this);
                for (String s : values) {
                    builder.addItem(s);
                }
                builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder
                        .OnSheetItemClickListener() {


                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position,
                                        String tag) {
                        switch (position) {
                            case 0: {// 网络图片
                                Glide.with(getApplicationContext()).load(mPath).into(mDataBinding
                                        .mGlideIV);
                                break;
                            }
                            case 1: {// 本地图片
                                File file = new File(Environment.getExternalStorageDirectory() +
                                        "/glide_test.jpg");
                                Glide.with(getApplicationContext()).load(file).into(mDataBinding
                                        .mGlideIV);
                                break;
                            }
                            case 2: {// gif图片
                                File file = new File(Environment.getExternalStorageDirectory() +
                                        "/glide_test_gif.gif");
                                Glide.with(getApplicationContext()).load(file).asGif()
                                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).into
                                        (mDataBinding.mGlideIV);
                                break;
                            }
                            case 3: {// 缩略图
                                Glide.with(getApplicationContext()).load(mPath).thumbnail(0.5f)
                                        .into(mDataBinding
                                                .mGlideIV);
                                break;
                            }
                            case 4: {// 模糊
                                Glide.with(getApplicationContext()).load(mPath).bitmapTransform
                                        (new BlurTransformation(getApplicationContext(), 10))
                                        .into(mDataBinding
                                                .mGlideIV);
                                break;
                            }
                            case 5: {// 圆角
                                Glide.with(getApplicationContext()).load(mPath).bitmapTransform
                                        (new RoundedCornersTransformation(getApplicationContext()
                                                , 50, 10, RoundedCornersTransformation.CornerType
                                                .ALL)).into(mDataBinding.mGlideIV);
                                break;
                            }
                            case 6: {// 遮盖
                                Glide.with(getApplicationContext()).load(mPath).bitmapTransform
                                        (new MaskTransformation(getApplicationContext(), R.mipmap
                                                .ic_launcher_round)).into(mDataBinding.mGlideIV);
                                break;
                            }
                            case 7: {// 灰度
                                Glide.with(getApplicationContext()).load(mPath).bitmapTransform
                                        (new GrayscaleTransformation(getApplicationContext()))
                                        .into(mDataBinding.mGlideIV);
                                break;
                            }
                            case 8: {// 圆形
                                Glide.with(getApplicationContext()).load(mPath).bitmapTransform
                                        (new CropCircleTransformation(getApplicationContext()))
                                        .into(mDataBinding.mGlideIV);
                                break;
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.build().show();
            }
        });
    }

    @Override
    protected void initView() {

    }
}
