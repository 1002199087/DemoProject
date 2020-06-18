package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.temporary.adapter.LogAdapter;
import com.temporary.bean.LogBean;
import com.temporary.bean.MeterDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LogAddActivity extends NewBaseActivity {
    @BindView(R.id.log_recycler)
    RecyclerView mLogRecycler;
    @BindView(R.id.placeholder_btn)
    Button mPlaceholderBtn;
    @BindView(R.id.change_visible_btn)
    Button mChangeVBtn;
    @BindView(R.id.clear_log_btn)
    Button mClearBtn;

    private RecyclerView.LayoutManager mLayoutManager;
    private LogAdapter mLogAdapter;
    private List<LogBean> mLogList;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LogAddActivity.class);
        return intent;
    }

    private Handler mMainHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String currentTime = String.valueOf(System.currentTimeMillis());
            if (Long.valueOf(currentTime) % 2 == 0) {
                addLogInfo("这是测试！" + currentTime, currentTime, null, 1);
            } else if (Long.valueOf(currentTime) % 3 == 0) {
                MeterDataBean meterDataBean = new MeterDataBean();
                meterDataBean.setMeterNumber("1901808690");
                addLogInfo(meterDataBean.getMeterNumber() + ":抄表异常！",
                        meterDataBean.getMeterNumber(), meterDataBean, 2);
            } else {
                /*bean.setInfo("这是普通文字测试！");
                bean.setMode(0);*/
                String btString = "HC-05 (98:D3:91:FD:AE:B2)";
                addLogInfo("是否重新连接蓝牙设备？\n" + btString,
                        btString, null, 3);
            }

            mMainHandler.sendEmptyMessageDelayed(0, 1000);
            return false;
        }
    });

    @Override
    public String getTopbarTitle() {
        return getString(R.string.log_increase_tip);
    }

    @Override
    public int getResId() {
        return R.layout.activity_log_add;
    }

    @Override
    public void initView() {
        mLogList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mLogAdapter = new LogAdapter(this, mLogList, new LogAdapter.LogAdapterListener() {
            @Override
            public void onMeterBeanClickedListener(MeterDataBean bean) {

            }

            @Override
            public void onKeyStringClickedListener(String keyString) {

            }
        });
        mLogRecycler.setLayoutManager(mLayoutManager);
        mLogRecycler.setAdapter(mLogAdapter);
    }

    @Override
    public void init() {
        mMainHandler.sendEmptyMessage(0);
    }

    @Override
    public int getTopbarMode() {
        return ONLY_BACK;
    }

    @OnClick({R.id.placeholder_btn, R.id.change_visible_btn, R.id.clear_log_btn})
    protected void onClicked(View view) {
        switch (view.getId()) {
            case R.id.placeholder_btn: {
                if (mMainHandler.hasMessages(0))
                    mMainHandler.removeMessages(0);
                else
                    mMainHandler.sendEmptyMessage(0);
                break;
            }
            case R.id.change_visible_btn: {
                if (mPlaceholderBtn.getVisibility() == View.VISIBLE) {
                    mPlaceholderBtn.setVisibility(View.GONE);
                } else {
                    mPlaceholderBtn.setVisibility(View.VISIBLE);
                }
                if (mLogList.size() > 0) {
                    mLogRecycler.scrollToPosition(mLogList.size() - 1);
                }
                break;
            }
            case R.id.clear_log_btn: {
                mMainHandler.removeMessages(0);
                mLogList.clear();
                mLogAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 增加打印信息
     *
     * @param info
     * @param keyString
     * @param bean
     * @param mode
     */
    private void addLogInfo(String info, String keyString, MeterDataBean bean, int mode) {
        LogBean logBean = new LogBean.Builder()
                .info(info)
                .keyString(keyString)
                .meterDataBean(bean)
                .mode(mode)
                .build();
        mLogList.add(logBean);
        mLogAdapter.notifyDataSetChanged();
        mLogRecycler.scrollToPosition(mLogList.size() - 1);
    }
}
