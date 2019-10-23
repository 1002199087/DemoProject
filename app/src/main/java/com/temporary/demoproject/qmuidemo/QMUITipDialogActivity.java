package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.temporary.demoproject.R;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUITipDialogActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.list_view)
    ListView mListView;

    private QMUITipDialog mQMUITipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuitip_dialog);
        ButterKnife.bind(this);

        initTopbar();

        String[] styleTipDialog = getResources().getStringArray(R.array.qmui_tipdialog_list);
        List<String> styleList = Arrays.asList(styleTipDialog);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                R.layout.customer_textview_layout_two, styleList);
        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("wyy", "QMUITipDialogActivity onItemClick i = " + i);
                switch (i) {
                    case 0:{// Loading 类型提示框
                        mQMUITipDialog = new QMUITipDialog.Builder(QMUITipDialogActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在加载")
                                .create();
                        break;
                    }
                    case 1:{// 成功提示类型提示框
                    mQMUITipDialog = new QMUITipDialog.Builder(QMUITipDialogActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .setTipWord("发送成功")
                                .create();
                        break;
                    }
                    case 2:{// 失败提示类型提示框
                        mQMUITipDialog = new QMUITipDialog.Builder(QMUITipDialogActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                                .setTipWord("发送失败")
                                .create();
                        break;
                    }
                    case 3:{// 信息提示类型提示框
                        mQMUITipDialog = new QMUITipDialog.Builder(QMUITipDialogActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                                .setTipWord("请勿重复操作")
                                .create();
                        break;
                    }
                    case 4:{// 单独图片类型提示框
                        mQMUITipDialog = new QMUITipDialog.Builder(QMUITipDialogActivity.this)
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                                .create();
                        break;
                    }
                    case 5:{// 单独文字类型提示框
                        mQMUITipDialog = new QMUITipDialog.Builder(QMUITipDialogActivity.this)
                                .setTipWord("请勿重复操作")
                                .create();
                        break;
                    }
                    case 6:{// 自定义内容提示框
                        mQMUITipDialog = new QMUITipDialog.CustomBuilder(QMUITipDialogActivity.this)
                                .setContent(R.layout.customer_text_layout)
                                .create();
                        break;
                    }
                }
                mQMUITipDialog.show();
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mQMUITipDialog.dismiss();
                    }
                }, 2000);
            }
        });
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_tipdialog_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
