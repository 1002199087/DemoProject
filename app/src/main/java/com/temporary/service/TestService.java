package com.temporary.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2019/3/20 0020.
 */

public class TestService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("wyy", "TestService onCreate ");
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.e("wyy", "TestService onStartCommand ");
        flags = START_STICKY;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e("wyy", "TestService run sss" + intent.getStringExtra("demo_values"));
                }
            }
        }).start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new TestBinder();
    }

    public class TestBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("wyy", "TestService onUnbind ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("wyy", "TestService onDestroy ");
        super.onDestroy();
    }
}
