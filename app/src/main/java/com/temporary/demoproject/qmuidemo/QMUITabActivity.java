package com.temporary.demoproject.qmuidemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QMUITabActivity extends AppCompatActivity {


    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.fixed_width_button)
    QMUIRoundButton mFixedWidthBtn;
    @BindView(R.id.content_adaptation_button)
    QMUIRoundButton mContentAdaptationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuitab);
        ButterKnife.bind(this);

        initTopbar();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_tab_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.fixed_width_button, R.id.content_adaptation_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fixed_width_button: {
                //Intent intent = new Intent(this, QMUITabFixedActivity.class);
                startActivity(QMUITabFixedActivity.getInstanceIntent(this));
                break;
            }
            case R.id.content_adaptation_button: {
                //Intent intent = new Intent(this, QMUITabScrollActivity.class);
                startActivity(QMUITabScrollActivity.getInstanceIntent(this));
                break;
            }
        }
    }
}
