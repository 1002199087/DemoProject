package com.temporary.demoproject.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.temporary.demoproject.R;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import is.arontibo.library.ElasticDownloadView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

    @BindView(R.id.content_textview)
    TextView mContentTV;
    @BindView(R.id.m_view)
    ElasticDownloadView mProgressView;
    @BindView(R.id.test_button)
    Button mTestBtn;
    @BindView(R.id.pick_view)
    DayPickerView mDayPickerView;
    @BindView(R.id.calendarview)
    com.prolificinteractive.materialcalendarview.MaterialCalendarView calendarview;
    private Unbinder mUnbinder;
    private int j = 0;
    private ProgressViewHandler mProgressViewHandler;

    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mProgressViewHandler = new ProgressViewHandler(this);
        mUnbinder = ButterKnife.bind(this, view);
        mDayPickerView.setController(new DatePickerController() {
            @Override
            public int getMaxYear() {
                return Calendar.getInstance().get(Calendar.YEAR) + 1;
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {

            }

            @Override
            public void onDateRangeSelected(
                    SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

            }
        });
        calendarview.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget,
                                       @NonNull CalendarDay date, boolean selected) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private class ProgressViewHandler extends Handler {
        private WeakReference<Fragment> weakReference;

        public ProgressViewHandler(Fragment fragment) {
            weakReference = new WeakReference<Fragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (weakReference.get() == null) return;
            int i = (int) msg.obj;
            mProgressView.setProgress(i);
            if (i == 100) {
                mProgressView.success();
            }
        }
    }

    @OnClick(R.id.test_button)
    public void onViewClicked() {
        mProgressView.startIntro();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; ) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    Message message = mProgressViewHandler.obtainMessage();
                    message.what = 0;
                    message.obj = i;
                    message.sendToTarget();
                }
            }
        }).start();
    }
}
