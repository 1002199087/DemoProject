package com.temporary.demoproject;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.temporary.service.AlarmService;

public class AlarmManagerActivity extends NewBaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AlarmManagerActivity.class);
        return intent;
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.alarm_manager_demo_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_alarm_manager;
    }

    @Override
    public void initView() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init() {
        Intent intent2 = new Intent(this, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent2, 0);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + 20000,
                pendingIntent);
    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }
}
