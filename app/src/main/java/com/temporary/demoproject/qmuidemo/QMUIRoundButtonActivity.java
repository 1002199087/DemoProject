package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUIRoundButtonActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuiround_button);
        ButterKnife.bind(this);

        QMUIStatusBarHelper.translucent(this);

        initTopbar();
    }

    private void initTopbar() {
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.button_demo_text));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
