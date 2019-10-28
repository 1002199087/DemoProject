package com.temporary.demoproject;

import android.app.Application;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wyy on 2019/3/1 0001.
 */

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);*/

        ViseLog.getLogConfig()
                .configAllowLog(true)
                .configShowBorders(true)
                .configTagPrefix("wyy")
                .configLevel(Log.VERBOSE);
        ViseLog.plant(new LogcatTree());
    }
}
