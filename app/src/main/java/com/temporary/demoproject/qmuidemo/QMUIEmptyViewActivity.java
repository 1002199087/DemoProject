package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUIEmptyViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.empty_view)
    QMUIEmptyView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuiempty_view);
        ButterKnife.bind(this);

        initTopbar();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_emptyview_button));
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
                        final String[] items = getResources().getStringArray(R.array.qmui_emptyview_texts);
                        QMUIBottomSheet.BottomListSheetBuilder builder =
                                new QMUIBottomSheet.BottomListSheetBuilder(QMUIEmptyViewActivity.this);
                        for (String s : items) {
                            builder.addItem(s);
                        }
                        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                switch (position) {
                                    case 0: {// 显示两行提示语
                                        mEmptyView.show(items[position], "第二行");
                                        break;
                                    }
                                    case 1: {// 显示一行提示语
                                        mEmptyView.show(items[position], null);
                                        break;
                                    }
                                    case 2: {// 显示Loading
                                        mEmptyView.show(true);
                                        break;
                                    }
                                    case 3: {// 显示一行文字和按钮
                                        mEmptyView.show(false, items[position], null,
                                                "按键", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Toast.makeText(QMUIEmptyViewActivity.this,
                                                                "测试", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        break;
                                    }
                                    case 4: {// 显示两行文字和按钮
                                        mEmptyView.show(false, items[position],
                                                "第二行", "按键",
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Toast.makeText(QMUIEmptyViewActivity.this,
                                                                "测试", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
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
