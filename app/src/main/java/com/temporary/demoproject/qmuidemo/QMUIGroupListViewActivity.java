package com.temporary.demoproject.qmuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.temporary.demoproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUIGroupListViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.group_list_view)
    QMUIGroupListView mGroupListView;

    private final int GROUPLIST_NORMAL = 0;
    private final int GROUPLIST_NORMAL_RIGHT = 1;
    private final int GROUPLIST_NORMAL_BELOW = 2;
    private final int GROUPLIST_NORMAL_CHEVRON = 3;
    private final int GROUPLIST_NORMAL_SWITCH = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuigroup_list_view);
        ButterKnife.bind(this);

        initTopbar();
        initGroupListView();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTopbar.setTitle(getResources().getString(R.string.qmui_grouplist_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initGroupListView() {
        QMUICommonListItemView normalItemView = mGroupListView.createItemView("item 1");
        normalItemView.setTag(GROUPLIST_NORMAL);

        QMUICommonListItemView normalItemViewRight = mGroupListView.createItemView("item 2");
        normalItemViewRight.setDetailText("在右方的详细信息");
        normalItemViewRight.setTag(GROUPLIST_NORMAL_RIGHT);

        QMUICommonListItemView normalItemViewBelow = mGroupListView.createItemView("item 3");
        normalItemViewBelow.setDetailText("在下方的详细信息");
        normalItemViewBelow.setOrientation(QMUICommonListItemView.VERTICAL);
        normalItemViewBelow.setTag(GROUPLIST_NORMAL_BELOW);

        QMUICommonListItemView itemWithChevron = mGroupListView.createItemView("item 4");
        itemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemWithChevron.setTag(GROUPLIST_NORMAL_CHEVRON);
        itemWithChevron.setDetailText("在右方的详细信息");

        QMUICommonListItemView itemWithSwitch = mGroupListView.createItemView("item 5");
        itemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemWithSwitch.setTag(GROUPLIST_NORMAL_SWITCH);
        itemWithSwitch.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        //itemWithSwitch.getSwitch().setButtonDrawable(getResources().getDrawable(R.drawable.radio_button_bg));

        QMUICommonListItemView itemWithCustom = mGroupListView.createItemView("item 6");
        itemWithCustom.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        QMUILoadingView loadingView = new QMUILoadingView(this);
        itemWithCustom.addAccessoryCustomView(loadingView);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch ((Integer)view.getTag()) {
                    case GROUPLIST_NORMAL:{
                        break;
                    }
                    case GROUPLIST_NORMAL_RIGHT:{
                        break;
                    }
                    case GROUPLIST_NORMAL_BELOW:{
                        break;
                    }
                }
            }
        };

        QMUIGroupListView.newSection(this)
                .setDescription("Section 1 的描述")
                .setTitle("Section 1: 默认提供的样式")
                .addItemView(normalItemView, onClickListener)
                .addItemView(normalItemViewRight, onClickListener)
                .addItemView(normalItemViewBelow, onClickListener)
                .addItemView(itemWithChevron, onClickListener)
                .addItemView(itemWithSwitch, onClickListener)
                .addTo(mGroupListView);

        itemWithChevron.setDetailText("更新");

        QMUIGroupListView.newSection(this)
                .setTitle("Section 1: 自定义的样式")
                .addItemView(itemWithCustom, onClickListener)
                .addTo(mGroupListView);
    }
}
