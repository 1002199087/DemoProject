package com.temporary.demoproject.qmuidemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.adapter.HistoryAdapter;
import com.temporary.demoproject.R;
import com.temporary.greendao.DBManager;
import com.temporary.greendao.SearcherHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchLayoutActivity extends AppCompatActivity {
    @BindView(R.id.edit_text)
    EditText mEditText;
    /*@BindView(R.id.search_view)
    SearchView mSearchView;*/

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.edit_search_layout)
    RelativeLayout mEditSearchLayout;
    @BindView(R.id.ok_or_cancel_button)
    Button mOkOrCancelBtn;
    @BindView(R.id.clear_textview)
    TextView mClearTV;
    @BindView(R.id.history_recyclerview)
    RecyclerView mHistoryRV;

    private boolean isOkBtn = false;

    private String mSearchStr = null;
    private final int SEARCH_RESQUEST_CODE = 0;

    private DBManager mDBManager;
    private HistoryAdapter mHistoryAdapter;
    private List<SearcherHistory> mHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        ButterKnife.bind(this);

        initTopbar();
        demoCode();

        mDBManager = DBManager.getInstance(getApplicationContext());
        mHistoryList = mDBManager.queryHistory();
        mHistoryAdapter = new HistoryAdapter(this, mHistoryList);
        mHistoryAdapter.setIHistoryAdapter(new HistoryAdapter.IHistoryAdapter() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("search_input_result",
                        mHistoryAdapter.getHistoryList().get(position).getMHistory());
                setResult(2, intent);
                finish();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mHistoryRV.setLayoutManager(linearLayoutManager);
        mHistoryRV.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        mHistoryRV.setItemAnimator(new DefaultItemAnimator());
        mHistoryRV.setAdapter(mHistoryAdapter);
    }

    private void initTopbar() {
        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) mEditSearchLayout.getLayoutParams();
        params.height = QMUIDisplayHelper.getActionBarHeight(this);
        mEditSearchLayout.setLayoutParams(params);

        //QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.search_layout_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Map<String, List<String>> map = new HashMap<>();
    }

    private void demoCode() {
        mEditText.clearFocus();
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    if (charSequence.length() == 0) {
                        mOkOrCancelBtn.setText(getResources().getString(R.string.popupwindow_cancel_text));
                        isOkBtn = false;
                    } else {
                        mOkOrCancelBtn.setText(getResources().getString(R.string.popupwindow_ok_text));
                        isOkBtn = true;
                    }
                } else {
                    mOkOrCancelBtn.setText(getResources().getString(R.string.popupwindow_cancel_text));
                    isOkBtn = false;
                }
                mSearchStr = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_SEND) {
                    if (isOkBtn) {
                        getResult(mSearchStr);
                    }
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void getResult(String searchStr) {
        if (!mDBManager.isHistoryExist(searchStr)) {
            SearcherHistory searcherHistory = new SearcherHistory();
            searcherHistory.setMHistory(searchStr);
            mDBManager.insertHistory(searcherHistory);
        }

        Intent intent = new Intent();
        intent.putExtra("search_input_result", searchStr);
        setResult(2, intent);
    }

    @OnClick({R.id.ok_or_cancel_button, R.id.clear_textview})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ok_or_cancel_button: {
                if (isOkBtn) {
                    getResult(mSearchStr);
                }
                finish();
                break;
            }
            case R.id.clear_textview: {// 清除历史记录
                mDBManager.deleteAllHistory();
                mHistoryList.clear();
                mHistoryAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
