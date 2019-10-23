package com.temporary.presenter;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by Dee on 2018/7/10.
 */

public class BasePresenter<V> {
    protected WeakReference<V> mWeakReference;
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handleNetMessage(msg);
        }
    };

    public Handler getHandler() {
        return mHandler;
    }

    public void attach(V view) {
        mWeakReference = new WeakReference<V>(view);
    }

    public void handleNetMessage(Message msg) {

    }

    public V getObtainView() {
        return isAttach() ? mWeakReference.get() : null;
    }

    private boolean isAttach() {
        return mWeakReference != null && mWeakReference.get() != null;
    }

    public void detach(){
        if (isAttach()) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
