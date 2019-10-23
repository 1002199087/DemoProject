package com.temporary.demoproject;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.temporary.bean.ExpressageData;
import com.temporary.bean.ExpressageNetDao;
import com.temporary.demoproject.databinding.ActivityMvpandRetrofitAndRxJavaBinding;
import com.temporary.presenter.MVPAndRetrofitAndRxJavaPresenter;
import com.temporary.viewmodel.IMVPAndRetrofitAndRxJavaView;

public class MVPAndRetrofitAndRxJavaActivity extends
        SimpleBaseActivity<ActivityMvpandRetrofitAndRxJavaBinding> implements
        IMVPAndRetrofitAndRxJavaView {
    private MVPAndRetrofitAndRxJavaPresenter mPresenter;

    private StringBuffer mStringBuffer = new StringBuffer();
    public ObservableField<String> mTextViewContent = new ObservableField<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_mvpand_retrofit_and_rx_java;
    }

    @Override
    protected void initTopbar() {
        mDataBinding.mQmuiTopbar.setTitle(getString(R.string.mvp_and_retrofit_and_rxjava_title));
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
        mDataBinding.setActivity(this);

        mPresenter = new MVPAndRetrofitAndRxJavaPresenter();
        mPresenter.attach(this);
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mSimpleBtn: {// 基本应用
                mPresenter.requestExpressageInfo("zhongtong", "75134313598774");
                break;
            }
            case R.id.mMapBtn: {// Map 应用
                mPresenter.requestExpressDaoListInInfo("zhongtong", "75134313598774");
                break;
            }
            case R.id.mFlatMapBtn: {// FlatMap 应用
                mStringBuffer.delete(0, mStringBuffer.length());
                mPresenter.requestExpressDaoInList("zhongtong", "75134313598774");
                break;
            }
        }
    }

    @Override
    public void updateUI(ExpressageNetDao expressageNetDao) {
        mTextViewContent.set(expressageNetDao.getCom());
    }

    @Override
    public void updateListSize(int count) {
        mTextViewContent.set(String.valueOf(count));
    }

    @Override
    public void updateInfoInList(ExpressageData expressageData) {
        mStringBuffer.append(expressageData.getFtime() + "\n");
        mTextViewContent.set(String.valueOf(mStringBuffer));
    }
}
