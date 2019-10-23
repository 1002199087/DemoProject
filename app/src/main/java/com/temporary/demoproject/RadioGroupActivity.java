package com.temporary.demoproject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.fragment.OneFragment;
import com.temporary.demoproject.fragment.TwoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RadioGroupActivity extends AppCompatActivity {
    @BindView(R.id.one_radiobutton)
    RadioButton mOneRB;
    @BindView(R.id.two_radiobutton)
    RadioButton mTwoRB;
    @BindView(R.id.test_radiogroup)
    RadioGroup mTestRG;
    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.container)
    FrameLayout container;
    private OneFragment mOneFragment;
    private TwoFragment mTwoFragment;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group);

        QMUIStatusBarHelper.translucent(this);

        //QMUIStatusBarHelper.setStatusBarDarkMode(this);

        ButterKnife.bind(this);

        mOneFragment = new OneFragment();
        mTwoFragment = new TwoFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.container, mOneFragment);
        mFragmentTransaction.add(R.id.container, mTwoFragment);
        mFragmentTransaction.hide(mTwoFragment);
        mFragmentTransaction.commit();
        mTestRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mFragmentTransaction = mFragmentManager.beginTransaction();
                switch (i) {
                    case R.id.one_radiobutton: {
                        Log.e("wyy", "RadioGroupActivity onCheckedChanged one");
                        mFragmentTransaction.show(mOneFragment);
                        mFragmentTransaction.hide(mTwoFragment);
                        break;
                    }
                    case R.id.two_radiobutton: {
                        Log.e("wyy", "RadioGroupActivity onCheckedChanged two");
                        mFragmentTransaction.show(mTwoFragment);
                        mFragmentTransaction.hide(mOneFragment);
                        break;
                    }
                }
                mFragmentTransaction.commit();
            }
        });

        View view = new View(this);

        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle("状态栏");
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
