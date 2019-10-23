package com.temporary.demoproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.temporary.adapter.ArticleAdapter;
import com.temporary.adapter.DailyAdapter;
import com.temporary.custom.Article;
import com.temporary.custom.group.GroupItemDecoration;
import com.temporary.custom.group.GroupRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarViewActivity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    CalendarView mCalendarView;
    @BindView(R.id.date_textview)
    TextView mDateTV;
    @BindView(R.id.recyclerView)
    GroupRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);
        ButterKnife.bind(this);

        //mCalendarView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mCalendarView.setBackground(getResources().getColor(R.color.theme_color_light),
                getResources().getColor(R.color.theme_color_light),
                getResources().getColor(R.color.theme_color_light));
        mCalendarView.setWeeColor(getResources().getColor(R.color.theme_color_light),
                getResources().getColor(R.color.hint_text_color));
        mDateTV.setText(mCalendarView.getCurYear() + "-" + mCalendarView.getCurMonth() + "-" + mCalendarView.getCurDay());
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                mDateTV.setText(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
            }
        });

        mCalendarView.setTextColor(getResources().getColor(R.color.theme_color_blue),
                getResources().getColor(android.R.color.white),
                getResources().getColor(R.color.hint_color_light),
                getResources().getColor(android.R.color.white),
                getResources().getColor(R.color.hint_color_light));
        mCalendarView.setSelectedColor(getResources().getColor(R.color.theme_color_blue),
                getResources().getColor(android.R.color.white),
                getResources().getColor(android.R.color.white));

        DailyAdapter mDailyAdapter = new DailyAdapter(this);

        mRecyclerView = (GroupRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new ArticleAdapter(this));
        mRecyclerView.notifyDataSetChanged();
    }
}
