package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDirection;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QMUIViewHelperActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.qmui_viewhelper_fade_in_and_out_button)
    QMUIRoundButton mQmuiViewhelperFadeBtn;
    @BindView(R.id.qmui_viewhelper_Slide_in_and_out_button)
    QMUIRoundButton mQmuiViewhelperSlideBtn;
    @BindView(R.id.fade_textview)
    TextView mFadeTV;
    @BindView(R.id.slide_textview)
    TextView mSlideTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuiview_helper);
        ButterKnife.bind(this);

        initTopbar();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_viewhelper_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.qmui_viewhelper_fade_in_and_out_button,
            R.id.qmui_viewhelper_Slide_in_and_out_button})
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qmui_viewhelper_fade_in_and_out_button: {// fade
                if (mFadeTV.getVisibility() == View.GONE) {
                    mQmuiViewhelperFadeBtn.setText(getResources()
                            .getString(R.string.qmui_viewhelper_fade_in_button));
                    QMUIViewHelper.fadeIn(mFadeTV, 500, null, true);
                } else {
                    mQmuiViewhelperFadeBtn.setText(getResources()
                            .getString(R.string.qmui_viewhelper_fade_out_button));
                    QMUIViewHelper.fadeOut(mFadeTV, 500, null, true);
                }
                break;
            }
            case R.id.qmui_viewhelper_Slide_in_and_out_button: {// slide
                if (mSlideTV.getVisibility() == View.GONE) {
                    mQmuiViewhelperSlideBtn.setText(getResources().getString(R.string.qmui_viewhelper_Slide_in_button));
                    QMUIViewHelper.slideIn(mSlideTV, 500, null,
                            true, QMUIDirection.TOP_TO_BOTTOM);
                } else {
                    mQmuiViewhelperSlideBtn.setText(getResources().getString(R.string.qmui_viewhelper_Slide_out_button));
                    QMUIViewHelper.slideOut(mSlideTV, 500, null,
                            true, QMUIDirection.BOTTOM_TO_TOP);
                }
                break;
            }
        }
    }
}
