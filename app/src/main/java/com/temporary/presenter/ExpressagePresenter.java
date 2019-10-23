package com.temporary.presenter;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.temporary.bean.ExpressageData;
import com.temporary.bean.ExpressageNetDao;
import com.temporary.demoproject.viewmodel.IExpressageView;
import com.temporary.model.ExpressageModelImpl;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dee on 2018/9/3 0003.
 */

public class ExpressagePresenter extends BasePresenter<IExpressageView> {
    private Context mContext;
    private ExpressageModelImpl mExpressageModelImpl;

    public ExpressagePresenter(Context context) {
        this.mContext = context;
        mExpressageModelImpl = new ExpressageModelImpl();
    }

    /**
     * 查询物流
     *
     * @param type   物流公司
     * @param postid 物流单号
     */
    public void inquireExpressage(String type, String postid) {
        mExpressageModelImpl.inquireExpressage(type, postid, getHandler());
    }

    @Override
    public void handleNetMessage(Message msg) {
        super.handleNetMessage(msg);
        switch (msg.what) {
            case 0: {
                if (msg.obj instanceof ExpressageNetDao) {
                    List<ExpressageData> list = ((ExpressageNetDao) msg.obj).getData();
                    getObtainView().initExpressageRecyclerView(list);
                }
                break;
            }
            case 1: {
                Log.e("wyy", "ExpressagePresenter handleNetMessage " + msg.obj);
                break;
            }
        }
    }
}
