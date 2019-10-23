package com.temporary.demoproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.temporary.bean.EventBusDao;
import com.temporary.demoproject.databinding.ActivityEventBusBinding;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;


public class EventBusMainActivity extends SimpleBaseActivity<ActivityEventBusBinding> {
    private EventBusDao mEventBusDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_event_bus);
        //ButterKnife.bind(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_event_bus;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.eventbus_title));
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
        mEventBusDao = new EventBusDao("发布", 10086, "man");
        mDataBinding.setEventBusDao(mEventBusDao);
        mDataBinding.setActivity(this);

        EventBus.getDefault().registerSticky(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mPostEventBusBtn: {
                EventBus.getDefault().post(new EventBusDao("post 接收成功", 10086, "man"),
                        "main_eventbus");
                break;
            }
            case R.id.mStickyEventBusBtn: {
                EventBus.getDefault().postSticky(new EventBusDao("sticky 接收成功", 10086, "man"),
                        "main_eventbus");
                break;
            }
            case R.id.mRemoveEventBusBtn: {
                //EventBus.getDefault().removeStickyEvent();
                EventBus.getDefault().unregister(this);
                break;
            }
            case R.id.mEventBusSub1Btn: {
                Intent intent = new Intent(this, EventBusSub1Activity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Subscriber(mode = ThreadMode.POST, tag = "main_eventbus")
    private void updateTextView(EventBusDao dao) {
        mEventBusDao.clone(dao);
    }

    @Subscriber(mode = ThreadMode.POST, tag = "sub1_eventbus")
    public void updateMainTextView(EventBusDao dao) {
        Log.e("wyy", "EventBusMainActivity updateMainTextView ");
        mEventBusDao.clone(dao);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().removeStickyEvent(EventBusDao.class, "test");
        //EventBus.getDefault().unregister(this);
    }
}
