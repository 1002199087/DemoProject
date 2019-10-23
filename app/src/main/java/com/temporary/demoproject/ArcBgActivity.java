package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;

public class ArcBgActivity extends NewBaseActivity {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ArcBgActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.arc_bg_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_arc_bg;
    }

    @Override
    public void initView() {

    }

    @Override
    public void init() {

    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }
}
