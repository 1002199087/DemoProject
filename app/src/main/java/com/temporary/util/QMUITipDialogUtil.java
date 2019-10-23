package com.temporary.util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class QMUITipDialogUtil {
    private static QMUITipDialog mTipDialog;
    private static final int TIP_SHOW_TIME = 2000;

    public static void showOnlyWord(Context context, String word, View view) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setTipWord(word)
                .create();
        mTipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        }, 3000);
    }

    public static void showFailDialog(Context context, String tip, View view) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord(tip).create();
        mTipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        }, TIP_SHOW_TIME);
    }

    public static void showFailDialog(Context context, int tip, View view) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL).setTipWord(
                        context.getResources().getString(tip)).create();
        mTipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        }, TIP_SHOW_TIME);
    }

    public static void showSuccessDialog(Context context, int tip, View view) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord(
                        context.getResources().getString(tip)).create();
        mTipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        }, TIP_SHOW_TIME);
    }

    public static void showSuccessDialog(Context context, String tip, View view) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS).setTipWord(
                        tip).create();
        mTipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        }, TIP_SHOW_TIME);
    }

    public static void showLoadingDialog(Context context, int tip) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING).setTipWord(
                        context.getResources().getString(tip)).create();
        mTipDialog.show();
        mTipDialog.setCancelable(false);
    }

    public static void showTipDialog(Context context, int tip, View view) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO).setTipWord(
                        context.getResources().getString(tip)).create();
        mTipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTipDialog.dismiss();
            }
        }, TIP_SHOW_TIME);
    }

    public static void showTipDialog(Context context, int tip, View view, Runnable runnable,
                                     DialogInterface.OnCancelListener onCancelListener) {
        if (mTipDialog != null) mTipDialog.dismiss();
        mTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO).setTipWord(
                        context.getResources().getString(tip)).create();
        mTipDialog.show();
        mTipDialog.setOnCancelListener(onCancelListener);
        view.postDelayed(runnable, TIP_SHOW_TIME);
    }

    public static void dismiss() {
        if (mTipDialog != null) {
            mTipDialog.dismiss();
        }
    }

    public static boolean isShow() {
        if (mTipDialog != null) {
            return mTipDialog.isShowing();
        }
        return false;
    }
}
