package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUIRadiusImageViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.radius_imageview)
    QMUIRadiusImageView mRadiusImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuiradius_image_view);
        ButterKnife.bind(this);

        initTopbar();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_radiusimage_button));
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
                        final String[] items = getResources().getStringArray(R.array
                                .qmui_radiusimage_bottom_list);
                        QMUIBottomSheet.BottomListSheetBuilder builder =
                                new QMUIBottomSheet.BottomListSheetBuilder(
                                        QMUIRadiusImageViewActivity.this);
                        for (String s : items) {
                            builder.addItem(s);
                        }
                        builder.setOnSheetItemClickListener(new QMUIBottomSheet
                                .BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int
                                    position, String tag) {
                                switch (position) {
                                    case 0: {// 改变边框颜色与宽度
                                        mRadiusImageview.setBorderColor(getResources().getColor
                                                (android.R.color.black));
                                        mRadiusImageview.setBorderWidth(QMUIDisplayHelper.dp2px(
                                                QMUIRadiusImageViewActivity.this, 1));
                                        break;
                                    }
                                    case 1: {// 改变选中态边框颜色与宽度
                                        mRadiusImageview.setSelectedBorderColor(getResources()
                                                .getColor(android.R.color.black));
                                        mRadiusImageview.setSelectedBorderWidth(QMUIDisplayHelper
                                                .dp2px(
                                                QMUIRadiusImageViewActivity.this, 1));
                                        break;
                                    }
                                    case 2: {// 改变选中态 Mask 的颜色（带透明度）
                                        mRadiusImageview.setSelectedMaskColor(getResources()
                                                .getColor(android.R.color.black));
                                        break;
                                    }
                                    case 3: {// 手工切换选中态
                                        if (mRadiusImageview.isSelected()) {
                                            mRadiusImageview.setSelected(false);
                                        } else {
                                            mRadiusImageview.setSelected(true);
                                        }
                                        break;
                                    }
                                    case 4: {// 改变圆角大小
                                        mRadiusImageview.setCornerRadius(QMUIDisplayHelper.dp2px(
                                                QMUIRadiusImageViewActivity.this, 30));
                                        break;
                                    }
                                    case 5: {// 变为圆形
                                        mRadiusImageview.setCircle(true);
                                        break;
                                    }
                                    case 6: {// 变为椭圆形
                                        mRadiusImageview.setOval(true);
                                        break;
                                    }
                                    case 7: {// 禁止使用touchSelect态
                                        mRadiusImageview.setTouchSelectModeEnabled(false);
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
}
