package com.temporary.presenter;

import com.temporary.bean.ExpressageData;
import com.temporary.bean.ExpressageNetDao;
import com.temporary.model.MVPAndRetrofitAndRxJavaModelImpl;
import com.temporary.viewmodel.IMVPAndRetrofitAndRxJavaView;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2019/3/18 0018.
 */

public class MVPAndRetrofitAndRxJavaPresenter {
    private MVPAndRetrofitAndRxJavaModelImpl mModelImpl;
    private WeakReference<IMVPAndRetrofitAndRxJavaView> mWeakReference;


    public MVPAndRetrofitAndRxJavaPresenter() {
        this.mModelImpl = new MVPAndRetrofitAndRxJavaModelImpl();
    }

    public void attach(IMVPAndRetrofitAndRxJavaView v) {
        this.mWeakReference = new WeakReference<IMVPAndRetrofitAndRxJavaView>(v);
    }

    public IMVPAndRetrofitAndRxJavaView getObtainView() {
        return mWeakReference.get();
    }

    public void requestExpressageInfo(String type, String postid) {
        mModelImpl.getExpressageInfo(type, postid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExpressageNetDao>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ExpressageNetDao expressageNetDao) {
                        getObtainView().updateUI(expressageNetDao);
                    }
                });
    }

    public void requestExpressDaoListInInfo(String type, String postid) {
        mModelImpl.getExpressageInfo(type, postid)
                .map(new Func1<ExpressageNetDao, List<ExpressageData>>() {
                    @Override
                    public List<ExpressageData> call(ExpressageNetDao expressageNetDao) {
                        return expressageNetDao.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ExpressageData>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ExpressageData> expressageData) {
                        getObtainView().updateListSize(expressageData.size());
                    }
                });
    }

    public void requestExpressDaoInList(String type, String postid) {
        mModelImpl.getExpressageInfo(type, postid)
                .flatMap(new Func1<ExpressageNetDao, Observable<ExpressageData>>() {
                    @Override
                    public Observable<ExpressageData> call(ExpressageNetDao expressageNetDao) {
                        return Observable.from(expressageNetDao.getData());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ExpressageData>() {
                    @Override
                    public void call(ExpressageData expressageData) {
                        getObtainView().updateInfoInList(expressageData);
                    }
                });
    }
}
