package com.temporary.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.temporary.adapter.FlashairAdapter;
import com.temporary.demoproject.FlashairActivity;
import com.temporary.demoproject.R;
import com.temporary.demoproject.viewmodel.IFlashairView;
import com.temporary.model.FlashairModelImpl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dee on 2018/5/23.
 */

public class FlashairPresenter {
    private Context mContext;
    private FlashairModelImpl mFlashairModelImp;
    private IFlashairView mIFlashairView;
    private FlashairHandler mHandler;
    private List<String> mFilesList = new ArrayList<>();
    private FlashairAdapter mFlashairAdapter;
    private FlashairActivity mActivity;

    public FlashairPresenter(Context context, FlashairActivity activity) {
        this.mContext = context;
        mFlashairModelImp = new FlashairModelImpl();
        mHandler = new FlashairHandler(activity);
        this.mActivity = activity;
    }

    public void setIFlashairView(IFlashairView iFlashairView) {
        this.mIFlashairView = iFlashairView;
    }

    public void updateFlashairRV(List<String> list) {
        for (String str : list) {
            Log.e("wyy", "FlashairPresenter updateFlashairRV str = " + str);
        }
        mFlashairAdapter = mFlashairModelImp.getFlashairAdapter(list, mContext);
        mFlashairAdapter.setFlashairClickListener(new FlashairAdapter.IFlashairClickListener() {
            @Override
            public void click(final int position) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean flag = mFlashairModelImp.downFileFromFlashair(mFilesList.get(position));
                        //String string = mFlashairModelImp.delFileOnFlashair(mFilesList.get(position));
                        //mFilesList.remove(position);
                        Message message = mHandler.obtainMessage();
                        message.what = 1;
                        message.obj = flag ?
                                mContext.getResources().getString(R.string.download_success_toast_text)
                                : mContext.getResources().getString(R.string.download_fail_toast_text);
                        message.sendToTarget();
                        //Log.e("wyy", "FlashairPresenter run del result = " + string);
                    }
                }).start();
            }
        });
        mIFlashairView.updateFlashairRV(mFlashairAdapter);
    }

    public void showFilesList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String string = mFlashairModelImp.getFlashairFiles();
                Log.e("wyy", "FlashairPresenter run string = " + string);
                if (string != null) {
                    String[] strings = string.split(",");
                    for (int i = 0; i < strings.length; i++) {
                        if ((i - 1) % 5 == 0) {
                            if (strings[i].contains(".txt")
                                    || strings[i].contains(".IMA")) {
                                mFilesList.add(strings[i]);
                                boolean flag = mFlashairModelImp.downFileFromFlashair(strings[i]);
                                Log.e("wyy", "FlashairPresenter run i = " + i
                                        + " , strings[i] = " + strings[i] + " , flag = " + flag);
                                if (!flag) {
                                    Message msg = mHandler.obtainMessage();
                                    msg.what = 2;
                                    msg.sendToTarget();
                                    mActivity.finish();
                                    break;
                                }
                            }
                        }
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 0;
                    msg.obj = mFilesList;
                    msg.sendToTarget();
                }
            }
        }).start();
    }

    private class FlashairHandler extends Handler {
        WeakReference<FlashairActivity> reference;

        public FlashairHandler(FlashairActivity activity) {
            reference = new WeakReference<FlashairActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (reference.get() == null) return;
            switch (msg.what) {
                case 0: {/*显示 Flashair SD卡 上文件列表*/
                    List<String> list = (ArrayList) msg.obj;
                    updateFlashairRV(list);
                    break;
                }
                case 1: {/*下载 Flashair SD卡 上文件*/
                    String string = (String) msg.obj;
                    Toast.makeText(mContext, string, Toast.LENGTH_LONG).show();
                    mFlashairAdapter.notifyDataSetChanged();
                }
                case 2:{
                    String string = "断开连接，请重连！";
                    Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void uploadFileInFlashair() {
        mFlashairModelImp.uploadFileInFlashair("0000.IMA");
    }

}
