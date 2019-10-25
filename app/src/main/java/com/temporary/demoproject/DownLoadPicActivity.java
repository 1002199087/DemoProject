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
import com.temporary.bean.EventBusDao;
import com.temporary.factory.RetrofitFactory;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.util.QMUITipDialogUtil;
import com.vise.log.ViseLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DownLoadPicActivity extends NewBaseActivity {

    @BindView(R.id.download_button)
    Button mDownloadBtn;
    @BindView(R.id.pic_view)
    ImageView mPicView;
    @BindView(R.id.get_json_content)
    Button mGetJsonBtn;

    private DownLoadHandler mHandler;

    private final String BASE_URL = "http://10.1.5.240:8080/";
    private final String REQUEST_URL = "SpringBootDemo/requestPic";
    private final String FILE_NAME = "demo_pic.jpg";
    private final String JSON_URL = "SpringBootDemo/requestJsonContent";

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

    @OnClick({R.id.download_button, R.id.get_json_content})
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.download_button: {// 下载图片
                downLoad();
                break;
            }
            case R.id.get_json_content: {// 获取json格式的内容
                requestJsonContent(JSON_URL);
                break;
            }
        }
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
                        InputStream is = null;
                        FileOutputStream fos = null;
                        BufferedInputStream bis = null;
                        BufferedOutputStream bos = null;
                        try {
                            is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory(),
                                    FILE_NAME);
                            if (file.exists()) file.delete();
                            fos = new FileOutputStream(file);
                            bos = new BufferedOutputStream(fos);
                            bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                bos.write(buffer, 0, len);
                            }
                            bos.flush();

                            Message message = mHandler.obtainMessage();
                            message.obj = file;
                            message.sendToTarget();
                        } catch (Exception e) {
                            ViseLog.e(e.toString());
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) is.close();
                                if (bos != null) bos.close();
                                if (fos != null) fos.close();
                                if (bis != null) bis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

    private void requestJsonContent(String url) {
        RetrofitFactory.getInstance().getSpringBootInterface()
                .requestJsonContent(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventBusDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(EventBusDao eventBusDao) {
                        ViseLog.d(eventBusDao);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 清除缓存，否则图片不会改变
                Glide.get(DownLoadPicActivity.this).clearDiskCache();
            }
        }).start();
    }

    private class DownLoadHandler extends Handler {
        private WeakReference<DownLoadPicActivity> weakReference;

        public DownLoadHandler(DownLoadPicActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                mPicView.setVisibility(View.VISIBLE);
                File file = (File) msg.obj;
                Glide.with(DownLoadPicActivity.this).load(file).into(mPicView);
                QMUITipDialogUtil.showSuccessDialog(DownLoadPicActivity.this,
                        R.string.download_success_toast_text, mDownloadBtn);
            }
        }
    }
}
