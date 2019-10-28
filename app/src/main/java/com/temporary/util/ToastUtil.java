package com.temporary.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void makeText(Context context, int resId, int duration) {
        Toast toast = Toast.makeText(context, null, duration);
        toast.setText(resId);
        toast.show();
    }

    public static void makeText(Context context, String resString, int duration) {
        Toast toast = Toast.makeText(context, null, duration);
        toast.setText(resString);
        toast.show();
    }
}
