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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.temporary.bean.EventBusDao;
import com.temporary.bean.request.UploadInfoRequest;
import com.temporary.bean.response.SimpeResponseDao;
import com.temporary.factory.RetrofitFactory;
import com.temporary.network.customer.TestNetInterface;
import com.temporary.util.QMUITipDialogUtil;
import com.temporary.util.ToastUtil;
import com.vise.log.ViseLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SpringBootRequestActivity extends NewBaseActivity {

    @BindView(R.id.download_button)
    Button mDownloadBtn;
    @BindView(R.id.pic_view)
    ImageView mPicView;
    @BindView(R.id.get_json_content)
    Button mGetJsonBtn;
    @BindView(R.id.get_json_content_by_params)
    Button mGetJsonByParamsBtn;
    @BindView(R.id.get_json_content_by_params_with_id)
    Button mGetJsonByParamsWithIdBtn;
    @BindView(R.id.json_params_btn)
    Button mRequestWithJsonParamsBtn;
    @BindView(R.id.file_params_btn)
    Button mFileParamsBtn;
    @BindView(R.id.params_and_files_btn)
    Button mParamsAndFilesBtn;

    private DownLoadHandler mHandler;

    private final String BASE_URL = "http://10.1.5.240:8080/";
    private final String REQUEST_URL = "SpringBootDemo/requestPic";
    private final String FILE_NAME = "demo_pic.jpg";
    private final String JSON_URL = "SpringBootDemo/requestJsonContent";
    private final String JSON_URL_BY_PARAMS = "SpringBootDemo/requestJsonContentByParams";
    private final String JSON_BY_PARAMS_WITH_ID_URL = "SpringBootDemo" +
            "/requestJsonContentByParamsWithId/1989";
    private final String JSON_BY_JSON_PARAMS = "SpringBootDemo/requestJsonContentByJsonParams";
    private final String UPLOAD_FILES_URL = "SpringBootDemo/uploadFiles";
    private final String UPLOAD_PARAMS_AND_FILES_URL = "SpringBootDemo/uploadParamsAndFiles";

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SpringBootRequestActivity.class);
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

    @OnClick({R.id.download_button, R.id.get_json_content, R.id.get_json_content_by_params,
            R.id.get_json_content_by_params_with_id, R.id.json_params_btn, R.id.file_params_btn,
            R.id.params_and_files_btn})
    protected void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.params_and_files_btn: {// 上传带参数的文件
                QMUITipDialogUtil.showLoadingDialog(this, R.string.uploading_file_tip);
                List<String> fileNames = new ArrayList<>(Arrays.asList("1571038921662.png",
                        "demo_pic.jpg", "1571039208691.png"));
                List<File> files = new ArrayList<>();
                for (String fineName : fileNames) {
                    File file = new File(Environment.getExternalStorageDirectory() +
                            "/" + fineName);
                    files.add(file);
                }
                UploadInfoRequest request = new UploadInfoRequest();
                request.setMsg("This is Uploading~");
                request.setNum(fileNames.size() + "");

                requestUploadParamsAndFiles(UPLOAD_PARAMS_AND_FILES_URL, request, files);
                break;
            }
            case R.id.file_params_btn: {// 上传文件
                QMUITipDialogUtil.showLoadingDialog(this, R.string.uploading_file_tip);
                List<String> fileNames = new ArrayList<>(Arrays.asList("1571038921662.png",
                        "demo_pic.jpg", "1571039208691.png"));
                List<File> files = new ArrayList<>();
                for (String name : fileNames) {
                    File file = new File(Environment.getExternalStorageDirectory() +
                            "/" + name);
                    files.add(file);
                }
                requestUploadFile(UPLOAD_FILES_URL, files);
                break;
            }
            case R.id.json_params_btn: {// 获取json格式内容（带json参数）
                EventBusDao dao = new EventBusDao("today", 99, "man-woman");
                requestJsonContentWithJsonParams(JSON_BY_JSON_PARAMS, dao);
                break;
            }
            case R.id.get_json_content_by_params_with_id: {// 获取json格式内容（带参数，url带id）
                requestJsonContentByParamsWithId(JSON_BY_PARAMS_WITH_ID_URL, "today");
                break;
            }
            case R.id.get_json_content_by_params: {// 获取json格式内容（带参数）
                requestJsonContentByParams(JSON_URL_BY_PARAMS, "today", 99, "man-woman");
                break;
            }
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
                QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                        R.string.download_fail_toast_text, mDownloadBtn);
            }
        });
    }

    /**
     * 获取json格式的内容
     *
     * @param url
     */
    private void requestJsonContent(String url) {
        QMUITipDialogUtil.showLoadingDialog(this, R.string.loading_tip);
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
                        QMUITipDialogUtil.dismiss();
                        ViseLog.d(eventBusDao);
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        String result = gson.toJson(eventBusDao);
                        ToastUtil.makeText(SpringBootRequestActivity.this, result,
                                Toast.LENGTH_SHORT);
                    }
                });
    }

    /**
     * 获取json格式内容（带参数）
     *
     * @param url
     * @param name
     * @param age
     * @param sex
     */
    private void requestJsonContentByParams(String url, String name, int age, String sex) {
        QMUITipDialogUtil.showLoadingDialog(this, R.string.loading_tip);
        RetrofitFactory.getInstance().getSpringBootInterface()
                .requestJsonContent(url, name, age, sex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventBusDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e.toString());
                        QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                e.toString(), mGetJsonByParamsBtn);
                    }

                    @Override
                    public void onNext(EventBusDao eventBusDao) {
                        QMUITipDialogUtil.dismiss();
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        String result = gson.toJson(eventBusDao);
                        ToastUtil.makeText(SpringBootRequestActivity.this, result,
                                Toast.LENGTH_SHORT);
                    }
                });
    }

    /**
     * 获取json格式内容（带参数，url带id）
     *
     * @param url
     * @param name
     */
    private void requestJsonContentByParamsWithId(String url, String name) {
        QMUITipDialogUtil.showLoadingDialog(this, R.string.loading_tip);
        RetrofitFactory.getInstance().getSpringBootInterface()
                .requestJsonContent(url, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<EventBusDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e.toString());
                        QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                e.toString(), mGetJsonByParamsBtn);
                    }

                    @Override
                    public void onNext(EventBusDao eventBusDao) {
                        QMUITipDialogUtil.dismiss();
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        String result = gson.toJson(eventBusDao);
                        ToastUtil.makeText(SpringBootRequestActivity.this, result,
                                Toast.LENGTH_SHORT);
                    }
                });
    }

    /**
     * 获取json格式内容（带json参数）
     *
     * @param url
     * @param dao
     */
    private void requestJsonContentWithJsonParams(String url, EventBusDao dao) {
        QMUITipDialogUtil.showLoadingDialog(this, R.string.loading_tip);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(dao);
        RequestBody body = RetrofitFactory.getInstance().getJsonRequestBody(content);
        RetrofitFactory.getInstance().getSpringBootInterface()
                .requestJsonContentWithJsonParams(url, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpeResponseDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e.toString());
                        QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                e.toString(), mGetJsonByParamsBtn);
                    }

                    @Override
                    public void onNext(SimpeResponseDao response) {
                        QMUITipDialogUtil.dismiss();
                        if ("0000".equals(response.getCode())) {
                            ToastUtil.makeText(SpringBootRequestActivity.this, response.getMsg(),
                                    Toast.LENGTH_LONG);
                        }
                    }
                });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param files
     */
    private void requestUploadFile(String url, List<File> files) {
        Map<String, RequestBody> map = new HashMap<>();
        for (int i = 0; i < files.size(); i++) {
            map.put("files\";filename=\"" + files.get(i).getName(),
                    RetrofitFactory.getInstance().getFileRequestBody(files.get(i)));
        }
        RetrofitFactory.getInstance().getSpringBootInterface()
                .requestUploadFile(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpeResponseDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e.toString());
                        QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                e.toString(), mGetJsonByParamsBtn);
                    }

                    @Override
                    public void onNext(SimpeResponseDao response) {
                        if ("0000".equals(response.getCode())) {
                            QMUITipDialogUtil.showSuccessDialog(SpringBootRequestActivity.this,
                                    R.string.success_for_uploading_file_tip, mGetJsonByParamsBtn);
                        } else {
                            QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                    R.string.fail_for_uploading_file_tip, mGetJsonByParamsBtn);
                        }
                    }
                });
    }

    /**
     * 上传带参数的文件
     *
     * @param url
     * @param request
     * @param files
     */
    private void requestUploadParamsAndFiles(String url, UploadInfoRequest request,
                                             List<File> files) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String content = gson.toJson(request);
        ViseLog.d(content);
        RequestBody body = RetrofitFactory.getInstance().getJsonRequestBody(content);
        Map<String, RequestBody> map = new HashMap<>();
        for (File file : files) {
            map.put("files\";filename=\"" + file.getName(),
                    RetrofitFactory.getInstance().getFileRequestBody(file));
        }
        RetrofitFactory.getInstance().getSpringBootInterface()
                .requestUploadParamsAndFiles(url, body, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpeResponseDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ViseLog.e(e.toString());
                        QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                e.toString(), mGetJsonByParamsBtn);
                    }

                    @Override
                    public void onNext(SimpeResponseDao response) {
                        if ("0000".equals(response.getCode())) {
                            QMUITipDialogUtil.showSuccessDialog(SpringBootRequestActivity.this,
                                    R.string.success_for_uploading_file_tip, mGetJsonByParamsBtn);
                        } else {
                            QMUITipDialogUtil.showFailDialog(SpringBootRequestActivity.this,
                                    R.string.fail_for_uploading_file_tip, mGetJsonByParamsBtn);
                        }
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
                Glide.get(SpringBootRequestActivity.this).clearDiskCache();
            }
        }).start();
    }

    private class DownLoadHandler extends Handler {
        private WeakReference<SpringBootRequestActivity> weakReference;

        public DownLoadHandler(SpringBootRequestActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                mPicView.setVisibility(View.VISIBLE);
                File file = (File) msg.obj;
                Glide.with(SpringBootRequestActivity.this).load(file).into(mPicView);
                QMUITipDialogUtil.showSuccessDialog(SpringBootRequestActivity.this,
                        R.string.download_success_toast_text, mDownloadBtn);
            }
        }
    }
}
