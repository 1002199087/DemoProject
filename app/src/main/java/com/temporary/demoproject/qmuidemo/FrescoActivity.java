package com.temporary.demoproject.qmuidemo;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.R;

import butterknife.BindView;

public class FrescoActivity extends QMUIBaseActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.simple_drawee_view)
    SimpleDraweeView mSimpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = Uri.parse("http://ww1.sinaimg.cn/large/90bd89ffjw1es7bvew8f8j20pd0ay790.jpg");
        mSimpleDraweeView.setImageURI(uri);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fresco;
    }

    @Override
    public void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.fresco_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initBeforeContentView() {
        Fresco.initialize(getApplicationContext());
    }
}
