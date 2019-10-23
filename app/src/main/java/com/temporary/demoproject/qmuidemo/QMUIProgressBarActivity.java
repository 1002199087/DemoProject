package com.temporary.demoproject.qmuidemo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIProgressBar;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QMUIProgressBarActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.qmui_progressbar)
    QMUIProgressBar mQmuiPB;
    @BindView(R.id.qmui_progressbar_value_button)
    QMUIRoundButton mQmuiProgressbarValueBtn;

    private int mProgressBarValue = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuiprogress_bar);
        ButterKnife.bind(this);

        initTopbar();

        mQmuiPB.setQMUIProgressBarTextGenerator(new QMUIProgressBar.QMUIProgressBarTextGenerator() {
            @Override
            public String generateText(QMUIProgressBar progressBar, int value, int maxValue) {
                return value + "%";
            }
        });
        mQmuiPB.setProgress(mProgressBarValue);
        mQmuiProgressbarValueBtn.setText(String.format("进度 %d ", mProgressBarValue));
    }

    @OnClick({R.id.qmui_progressbar_value_button})
    protected void onViewClick(View view) {
        switch (mProgressBarValue) {
            case 0: {
                mProgressBarValue = 10;
                break;
            }
            case 10: {
                mProgressBarValue = 85;
                break;
            }
            case 85: {
                mProgressBarValue = 90;
                break;
            }
            case 90: {
                mProgressBarValue = 95;
                break;
            }
            case 95: {
                mProgressBarValue = 100;
                break;
            }
            case 100: {
                mProgressBarValue = 0;
                break;
            }
        }
        mQmuiPB.setProgress(mProgressBarValue);
        mQmuiProgressbarValueBtn.setText(String.format("进度 %d ", mProgressBarValue));
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_progressbar_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
