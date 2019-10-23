package com.temporary.demoproject.qmuidemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import is.arontibo.library.ElasticDownloadView;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.demo_webview)
    WebView mDemoWV;
    @BindView(R.id.webview_downloadview)
    ElasticDownloadView mWebviewDV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        initTopbar();

        mDemoWV.loadUrl("https://www.baidu.com");

        mWebviewDV.startIntro();
        WebSettings webSettings = mDemoWV.getSettings();

        // 支持javascript
        mDemoWV.getSettings().setJavaScriptEnabled(true);

        // 支持使用localStorage(H5页面的支持)
        mDemoWV.getSettings().setDomStorageEnabled(true);

        // 支持数据库
        mDemoWV.getSettings().setDatabaseEnabled(true);

        // 支持缓存
        mDemoWV.getSettings().setAppCacheEnabled(true);
        String appCachePath = getApplicationContext()
                .getDir("webview_cache", Context.MODE_PRIVATE).getPath();
        mDemoWV.getSettings().setAppCachePath(appCachePath);

        // 设置可以支持缩放
        mDemoWV.getSettings().setUseWideViewPort(true);

        // 扩大比例的缩放
        mDemoWV.getSettings().setSupportZoom(true);
        mDemoWV.getSettings().setBuiltInZoomControls(true);

        // 隐藏缩放按钮
        mDemoWV.getSettings().setDisplayZoomControls(true);

        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        // 隐藏滚动条
        mDemoWV.setVerticalScrollBarEnabled(false);
        mDemoWV.setHorizontalScrollBarEnabled(false);

        mDemoWV.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("wyy", "WebViewActivity onProgressChanged newProgress = " + newProgress);
                if (newProgress != 100) {
                    if (mWebviewDV.getVisibility() != View.VISIBLE) {
                        mWebviewDV.setVisibility(View.VISIBLE);
                    }
                    mWebviewDV.setProgress(newProgress);
                } else {
                    mWebviewDV.setProgress(newProgress);
                    mWebviewDV.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mWebviewDV.setVisibility(View.GONE);
                            mDemoWV.setVisibility(View.VISIBLE);
                        }
                    }, 1000);
                }
            }
        });

        mDemoWV.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.webview_layout_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mDemoWV.canGoBack()) {
            mDemoWV.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
