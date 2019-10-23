package com.temporary.demoproject.qmuidemo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QMUIDialogActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.message_dialog_button)
    QMUIRoundButton mMessageDialogBtn;
    @BindView(R.id.checkbox_message_dialog_button)
    QMUIRoundButton mCheckboxMessageDialogBtn;
    @BindView(R.id.edittext_dialog_button)
    QMUIRoundButton mEdittextDialogBtn;
    @BindView(R.id.menu_dialog_button)
    QMUIRoundButton mMenuDialogBtn;
    @BindView(R.id.checkable_dialog_button)
    QMUIRoundButton mCheckableDialogBtn;
    @BindView(R.id.multi_checkable_dialog_button)
    QMUIRoundButton mMultiCheckableDialogBtn;

    private int mDialogStyle = R.style.QMUI_Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuidialog);
        ButterKnife.bind(this);

        QMUIStatusBarHelper.translucent(this);
        initTopbar();
    }

    private void initTopbar() {
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_dialog_button));
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
                        final String[] items = getResources().getStringArray(R.array.qmui_dialog_style);
                        final QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet
                                .BottomListSheetBuilder(QMUIDialogActivity.this);
                        for (String s : items) {
                            builder.addItem(s);
                        }
                        builder.setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                Toast.makeText(QMUIDialogActivity.this, items[position],
                                        Toast.LENGTH_SHORT).show();
                                mDialogStyle = position == 0 ? R.style.QMUI_Dialog : R.style.m_dialog_style;
                                dialog.dismiss();
                            }
                        });
                        builder.build().show();
                    }
                });
    }

    @OnClick({R.id.message_dialog_button, R.id.checkbox_message_dialog_button,
            R.id.edittext_dialog_button, R.id.menu_dialog_button, R.id.checkable_dialog_button,
            R.id.multi_checkable_dialog_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.message_dialog_button: {//消息类型弹出框
                new QMUIDialog.MessageDialogBuilder(this)
                        .setTitle("消息类型弹出框Title")
                        .setMessage("消息类型弹出框Message")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                QMUIStatusBarHelper.setStatusBarDarkMode(QMUIDialogActivity.this);
                                //dialog.dismiss();
                            }
                        })
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            }
            case R.id.checkbox_message_dialog_button: {//checkbox消息类型弹出框
                final QMUIDialog.CheckBoxMessageDialogBuilder builder =
                        new QMUIDialog.CheckBoxMessageDialogBuilder(this);
                builder.setTitle("checkbox消息类型弹出框Title")
                        .setMessage("checkbox消息类型弹出框Message")
                        .addAction("确认", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                Toast.makeText(QMUIDialogActivity.this,
                                        "isChecked   = " + builder.isChecked(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            }
            case R.id.edittext_dialog_button: {//带输入框的弹出框
                final QMUIDialog.EditTextDialogBuilder builder =
                        new QMUIDialog.EditTextDialogBuilder(this);
                builder.setTitle("Edittext弹出框Title")
                        .setPlaceholder("请输入内容")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                String s = builder.getEditText().getText().toString();
                                Toast.makeText(QMUIDialogActivity.this, s,
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            }
            case R.id.menu_dialog_button: {//菜单弹出框
                final String[] strings = {"菜单选项1", "菜单选项2", "菜单选项3"};
                final QMUIDialog.MenuDialogBuilder builder = new QMUIDialog.MenuDialogBuilder(this);
                builder//.setTitle("菜单弹出框Title")
                        .addItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(QMUIDialogActivity.this, "选择了 " + strings[i],
                                        Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        })
                       /* .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })*/.show();
                break;
            }
            case R.id.checkable_dialog_button: {//单选菜单弹出框
                final String[] strings = {"单选菜单选项1", "单选菜单选项2", "单选菜单选项3"};
                QMUIDialog.CheckableDialogBuilder builder = new QMUIDialog.CheckableDialogBuilder(this);
                builder
                        //.setTitle("单选菜单弹出框Title")
                        .addItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(QMUIDialogActivity.this,
                                        "选择了 " + strings[i], Toast.LENGTH_SHORT).show();
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            }
            case R.id.multi_checkable_dialog_button: {//多选菜单弹出框
                String[] strings = {"多选菜单选项1", "多选菜单选项2", "多选菜单选项3",
                        "多选菜单选项4", "多选菜单选项5"};
                int[] checkedInts = {0, 2, 4};
                final QMUIDialog.MultiCheckableDialogBuilder builder =
                        new QMUIDialog.MultiCheckableDialogBuilder(this);
                builder.setTitle("多选菜单弹出框Title")
                        .setCheckedItems(checkedInts)
                        .addItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Toast.makeText(QMUIDialogActivity.this,
                                "一共选择了 " + builder.getCheckedItemIndexes().length + " 项",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {

                    }
                })
                        .create(mDialogStyle)
                        .show();
                break;
            }
        }
    }
}
