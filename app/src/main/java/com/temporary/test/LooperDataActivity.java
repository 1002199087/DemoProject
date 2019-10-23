package com.temporary.test;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.temporary.adapter.LooperDatasAdapter;
import com.temporary.demoproject.R;
import com.temporary.demoproject.viewmodel.ILooperDatasView;
import com.temporary.presenter.LooperDatasPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LooperDataActivity extends AppCompatActivity implements ILooperDatasView{
    @BindView(R.id.read_button)
    protected Button mReadBtn;
    @BindView(R.id.looper_datas_recyclerview)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.checked_button)
    protected Button mCheckedBtn;
    @BindView(R.id.add_tem_hum_button)
    protected Button mAddBtn;
    private LooperDatasPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_data);

        ButterKnife.bind(this);

        mPresenter = new LooperDatasPresenter(this);
    }

    @OnClick({R.id.read_button, R.id.checked_button, R.id.add_tem_hum_button})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.read_button:{
                mPresenter.setRecyclerView();
                break;
            }
            case R.id.checked_button:{
                mPresenter.getCheckedLooperDatas();
                break;
            }
            case R.id.add_tem_hum_button:{
                mPresenter.addTemAndHumToFile(Environment.getExternalStorageDirectory()+"/18M00000002009712_extra_info.txt",
                        "温度：" + 50 + "°C\r\n湿度：" + 50 + "%rh\r\n");
                break;
            }
        }
    }

    @Override
    public void setRecyclerViewLooperAdapter(LooperDatasAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }
}
