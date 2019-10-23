package com.temporary.demoproject.qmuidemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUIAnimationListView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QMUIAnimationListViewActivity extends AppCompatActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopbar;
    @BindView(R.id.anim_list_view)
    QMUIAnimationListView mAnimListView;
    private List<String> mItems = new ArrayList<>();
    private MyQMUIAdapter mMyQMUIAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qmuianimation_list_view);
        ButterKnife.bind(this);

        initTopbar();
    }

    private void initTopbar() {
        QMUIStatusBarHelper.translucent(this);
        mTopbar.setTitle(getResources().getString(R.string.qmui_animationlist_button));
        mTopbar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button button = mTopbar.addRightTextButton("添加", QMUIViewHelper.generateViewId());
        button.setTextColor(getResources().getColor(android.R.color.white));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimListView.manipulate(new QMUIAnimationListView.Manipulator<MyQMUIAdapter>() {
                    @Override
                    public void manipulate(MyQMUIAdapter adapter) {
                        mItems.add("item " + mItems.size());
                    }
                });
            }
        });
        initListView();
    }

    private void initListView() {
        for (int i = 0; i < 25; i++) {
            mItems.add("item " + i);
        }
        mMyQMUIAdapter = new MyQMUIAdapter(this, mItems);
        mAnimListView.setAdapter(mMyQMUIAdapter);
        mAnimListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int j = i;
                mAnimListView.manipulate(new QMUIAnimationListView.Manipulator<MyQMUIAdapter>() {
                    @Override
                    public void manipulate(MyQMUIAdapter adapter) {
                        mItems.remove(j);
                    }
                });
            }
        });
    }

    private class MyQMUIAdapter extends BaseAdapter {
        private List<String> list;
        private Context context;

        public MyQMUIAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(this.context)
                        .inflate(R.layout.customer_textview_layout_two,null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView.setText(this.list.get(i));
            return view;
        }

        class ViewHolder {
            TextView textView;

            public ViewHolder(View view) {
                textView = (TextView) view.findViewById(R.id.text);
            }
        }
    }
}
