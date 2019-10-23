package com.temporary.demoproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.temporary.adapter.FlashairAdapter;
import com.temporary.demoproject.viewmodel.IFlashairView;
import com.temporary.presenter.FlashairPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlashairActivity extends AppCompatActivity implements IFlashairView {
    @BindView(R.id.flashair_recyclerview)
    public RecyclerView mFlashairRV;
    private FlashairPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashair);
        ButterKnife.bind(this);
        requestPermission();
        init();
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET}, 0);
        }
    }

    private void init() {
        mPresenter = new FlashairPresenter(this, FlashairActivity.this);
        mPresenter.setIFlashairView(this);
        mPresenter.showFilesList();
    }

    @Override
    public void updateFlashairRV(FlashairAdapter adapter) {
        mFlashairRV.setAdapter(adapter);
        mFlashairRV.setLayoutManager(new LinearLayoutManager(this));
    }
}
