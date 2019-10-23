package com.temporary.demoproject.qmuidemo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExpandViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.expand_one_button)
    Button mExpandOneBtn;
    @BindView(R.id.project_name_textview)
    TextView mProjectNameTV;
    @BindView(R.id.date_textview)
    TextView mDateTV;
    @BindView(R.id.expand_one_layout)
    LinearLayout mExpandOneLayout;
    @BindView(R.id.expand_two_button)
    Button mExpandTwoBtn;
    @BindView(R.id.content_textview)
    TextView mContentTV;

    private int mExpandOneLayoutHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_view);
        ButterKnife.bind(this);

        initTopbar();

        mProjectNameTV.setText("item 1");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = simpleDateFormat.format(date);
        mDateTV.setText(dateStr);
        mContentTV.setText(getResources().getString(R.string.expand_content_text));

        mExpandOneLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mExpandOneLayoutHeight = mExpandOneLayout.getHeight();
                        Log.e("wyy", "ExpandViewActivity onGlobalLayout " + mExpandOneLayoutHeight);
                        mExpandOneLayout.setVisibility(View.GONE);
                        mExpandOneLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    @OnClick({R.id.expand_one_button, R.id.expand_two_button})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.expand_one_button: {
                if (mExpandOneLayout.getVisibility() == View.GONE) {
                    mExpandOneLayout.setVisibility(View.VISIBLE);
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mExpandOneLayoutHeight)
                            .setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            mExpandOneLayout.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                            mExpandOneLayout.requestLayout();
                            mExpandOneBtn.setText(getResources().getString(R.string.close_text_button));
                        }
                    });
                    valueAnimator.start();
                } else {
                    ValueAnimator valueAnimator = ValueAnimator.ofInt(mExpandOneLayoutHeight, 0)
                            .setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            mExpandOneLayout.getLayoutParams().height = (int) valueAnimator.getAnimatedValue();
                            mExpandOneLayout.requestLayout();
                            if ((int) valueAnimator.getAnimatedValue() == 0) {
                                mExpandOneLayout.setVisibility(View.GONE);
                                mExpandOneBtn.setText(getResources().getString(R.string.expand_text_button));
                            }
                        }
                    });
                    valueAnimator.start();
                }
                break;
            }
            case R.id.expand_two_button: {
                break;
            }
        }
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.expand_layout_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
