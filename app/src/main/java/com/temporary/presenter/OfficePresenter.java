package com.temporary.presenter;

import android.content.Context;

import com.temporary.model.OfficeImpl;

/**
 * Created by Dee on 2018/6/20.
 */

public class OfficePresenter extends BasePresenter {
    private Context mContext;
    private OfficeImpl mOfficeImpl;

    public OfficePresenter(Context context) {
        this.mContext = context;
        this.mOfficeImpl = new OfficeImpl();
    }

    public void readOfficeFile(String path, String type) {
        mOfficeImpl.readOfficeFile(mContext, path, type);
    }
}
