package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QMUIPopupActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.normal_popup_button)
    QMUIRoundButton mNormalPopupBtn;
    @BindView(R.id.list_popup_button)
    QMUIRoundButton mListPopupBtn;

    private QMUIPopup mQMUIPopup;
    private QMUIListPopup mQMUIListPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuipopup);
        ButterKnife.bind(this);

        initTopbar();
    }

    @OnClick({R.id.normal_popup_button, R.id.list_popup_button})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.normal_popup_button: {// normal
                initNormalPopup(view);
                break;
            }
            case R.id.list_popup_button: {// list
                initListPopup(view);
                break;
            }
        }
    }

    private void initNormalPopup(View view) {
        if (mQMUIPopup == null) {
            mQMUIPopup = new QMUIPopup(QMUIPopupActivity.this, QMUIPopup.DIRECTION_NONE);
            TextView textView = new TextView(QMUIPopupActivity.this);
            //textView.setLayoutParams(mQMUIPopup.generateLayoutParam(QMUIDisplayHelper.dp2px(QMUIPopupActivity.this, 250), WRAP_CONTENT));
            int padding = QMUIDisplayHelper.dp2px(QMUIPopupActivity.this, 50);
            textView.setPadding(padding / 5, padding / 5, padding / 5, padding / 5);
            textView.setText("normal popup");
            mQMUIPopup.setContentView(textView);
        }
        mQMUIPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mQMUIPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mQMUIPopup.show(view);
    }

    private void initListPopup(View view) {
        if (mQMUIListPopup == null) {
            final List<String> items = new ArrayList<>();
            for (int i = 0; i < 16; i++) {
                items.add("item " + i);
            }
            ArrayAdapter adapter = new ArrayAdapter(QMUIPopupActivity.this,
                    R.layout.customer_textview_layout_two, items);
            mQMUIListPopup = new QMUIListPopup(QMUIPopupActivity.this,
                    QMUIPopup.DIRECTION_TOP, adapter);
            mQMUIListPopup.create(QMUIDisplayHelper.dp2px(QMUIPopupActivity.this, 250),
                    QMUIDisplayHelper.dp2px(QMUIPopupActivity.this, 200),
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(QMUIPopupActivity.this,
                                    items.get(i), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        mQMUIListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mQMUIListPopup.setPreferredDirection(QMUIPopup.DIRECTION_TOP);
        mQMUIListPopup.show(view);
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_popup_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
