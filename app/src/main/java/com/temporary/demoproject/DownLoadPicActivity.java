package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.util.QMUITipDialogUtil;
import com.vise.log.ViseLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadPicActivity extends NewBaseActivity {

    @BindView(R.id.download_button)
    Button mDownloadBtn;
    @BindView(R.id.pic_view)
    ImageView mPicView;

    private DownLoadHandler mHandler;

    private final String BASE_URL = "http://www.005.tv/";
    private final String REQUEST_URL = "uploads/allimg/190702/66-1ZF2143214E2.png";
    private final String FILE_NAME = "demo_pic.jpg";

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, DownLoadPicActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    public String getTopbarTitle() {
        return getString(R.string.downLoad_pic_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_down_load_pic;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.download_button})
    protected void onViewClicked(View view) {
        downLoad();
    }

    private void downLoad() {
        QMUITipDialogUtil.showLoadingDialog(this, R.string.downloading_text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestNetInterface testNetInterface = retrofit.create(TestNetInterface.class);
        Call<ResponseBody> call = testNetInterface.getNetPic(REQUEST_URL);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory(),
                                    FILE_NAME);
                            if (file.exists()) file.delete();
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                            fos.close();
                            bis.close();
                            is.close();

                            Message message = mHandler.obtainMessage();
                            message.obj = file;
                            message.sendToTarget();
                        } catch (Exception e) {
                            ViseLog.e(e.toString());
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ViseLog.e(t.toString());
                QMUITipDialogUtil.showFailDialog(DownLoadPicActivity.this,
                        R.string.download_fail_toast_text, mDownloadBtn);
            }
        });
    }

    @Override
    public void init() {
        mHandler = new DownLoadHandler(this);
    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private class DownLoadHandler extends Handler {
        private WeakReference<DownLoadPicActivity> weakReference;

        public DownLoadHandler(DownLoadPicActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                File file = (File) msg.obj;
                Glide.with(DownLoadPicActivity.this).load(file).into(mPicView);
                QMUITipDialogUtil.showSuccessDialog(DownLoadPicActivity.this,
                        R.string.download_success_toast_text, mDownloadBtn);
            }
        }
    }
}
