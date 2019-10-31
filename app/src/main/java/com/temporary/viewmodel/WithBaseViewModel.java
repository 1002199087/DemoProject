package com.temporary.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.temporary.bean.EventBusDao;
import com.temporary.demoproject.R;
import com.temporary.factory.RetrofitFactory;
import com.temporary.util.QMUITipDialogUtil;
import com.vise.log.ViseLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WithBaseViewModel extends BaseViewModel {
    private Context mContext;
    public ObservableField<EventBusDao> mEventBusDao = new ObservableField<>();

    private WithBaseViewModelListener mListener;

    private final String JSON_URL = "SpringBootDemo/requestJsonContent";

    public WithBaseViewModel(Context mContext, WithBaseViewModelListener listener) {
        this.mContext = mContext;
        this.mListener = listener;
    }

    public void requestJsonContent() {
        requestJsonContent(JSON_URL);
    }

    /**
     * 获取json格式的内容
     *
     * @param url
     */
    private void requestJsonContent(String url) {
        QMUITipDialogUtil.showLoadingDialog(mContext, R.string.loading_tip);
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
                        mListener.onSuccessForLoadingListener();
                        ViseLog.d(eventBusDao);
                        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                        String result = gson.toJson(eventBusDao);
                        eventBusDao.setResult(result);
                        mEventBusDao.set(eventBusDao);
                    }
                });
    }

    public interface WithBaseViewModelListener {
        void onSuccessForLoadingListener();
    }
}
