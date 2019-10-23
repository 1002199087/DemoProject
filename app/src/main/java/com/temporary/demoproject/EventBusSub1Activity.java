package com.temporary.demoproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.temporary.bean.EventBusDao;
import com.temporary.demoproject.databinding.ActivityEventBusSub1Binding;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

public class EventBusSub1Activity extends SimpleBaseActivity<ActivityEventBusSub1Binding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_event_bus_sub1;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getResources().getString(R.string.eventbus_sub1_test));
        mDataBinding.mQmuiTopbar.addLeftBackImageButton().setOnClickListener(new View
                .OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        mDataBinding.setEventBusDao(new EventBusDao("发布", 10086, "man"));
        mDataBinding.setActivity(this);

        EventBus.getDefault().registerSticky(this);
    }

    @Subscriber(mode = ThreadMode.POST, tag = "main_eventbus")
    private void updateTextView(EventBusDao dao) {
        mDataBinding.setEventBusDao(dao);
        EventBus.getDefault().removeStickyEvent(EventBusDao.class, "main_eventbus");
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mStickyBtn: {
                EventBus.getDefault().post(new EventBusDao("sub1 postSticky 发布", 10086,
                        "man"), "sub1_eventbus");
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
