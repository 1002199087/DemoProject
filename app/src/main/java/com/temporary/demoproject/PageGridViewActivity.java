package com.temporary.demoproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.temporary.adapter.GridViewAdpter;
import com.temporary.adapter.ViewPagerAdapter;
import com.temporary.bean.GridItem;
import com.temporary.custom.WrapContentHeightGridView;
import com.temporary.custom.WrapContentHeightViewPager;

import java.util.ArrayList;
import java.util.List;

public class PageGridViewActivity extends AppCompatActivity {
    private WrapContentHeightViewPager viewPager;
    private LinearLayout group;//圆点指示器

    private List<GridItem> listDatas;
    private String[] proName = {"名称0", "名称1", "名称2", "名称3", "名称4", "名称5",
            "名称6", "名称7", "名称8", "名称9", "名称10", "名称11", "名称12", "名称13",
            "名称14", "名称15", "名称16", "名称17", "名称18", "名称19"};
    private TypedArray iconArray;

    private ImageView[] ivPoints;//小圆点图片的集合
    private int totalPage; //总的页数
    private int mPageSize = 8; //每页显示的最大的数量
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中
    //private int currentPage;//当前页

    public static Intent getIntent(Context context) {
        return new Intent(context, PageGridViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_grid_view);

        initView();
        initData();
    }

    private void initView() {
        // TODO Auto-generated method stub
        proName = getResources().getStringArray(R.array.main_items);
        iconArray = getResources().obtainTypedArray(R.array.main_item_icons);

        viewPager = (WrapContentHeightViewPager) findViewById(R.id.viewpager);
        group = (LinearLayout) findViewById(R.id.points);
        listDatas = new ArrayList<GridItem>();
        for (int i = 0; i < proName.length; i++) {
            listDatas.add(new GridItem(proName[i], iconArray.getResourceId(i, -1)));
        }
    }

    private void initData() {
        // TODO Auto-generated method stub
        //总的页数向上取整
        totalPage = (int) Math.ceil(listDatas.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for (int i = 0; i < totalPage; i++) {
            //每个页面都是inflate出一个新实例
            final WrapContentHeightGridView gridView =
                    (WrapContentHeightGridView) View.inflate(this, R.layout.item_grid_view, null);
            gridView.setAdapter(new GridViewAdpter(this, listDatas, i, mPageSize));
            //添加item点击监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int position, long arg3) {
                    // TODO Auto-generated method stub
                    Object obj = gridView.getItemAtPosition(position);
                    if (obj != null && obj instanceof GridItem) {

                    }
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewPager.setAdapter(new ViewPagerAdapter(viewPagerList));

        //添加小圆点
        ivPoints = new ImageView[totalPage];
        for (int i = 0; i < totalPage; i++) {
            //循坏加入点点图片组
            ivPoints[i] = new ImageView(this);
            if (i == 0) {
                ivPoints[i].setImageResource(R.mipmap.ic_point_normal);
            } else {
                ivPoints[i].setImageResource(R.mipmap.ic_point_unchecked);
            }
            ivPoints[i].setPadding(8, 8, 8, 8);
            group.addView(ivPoints[i]);
        }
        //设置ViewPager的滑动监听，主要是设置点点的背景颜色的改变
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //currentPage = position;
                for (int i = 0; i < totalPage; i++) {
                    if (i == position) {
                        ivPoints[i].setImageResource(R.mipmap.ic_point_normal);
                    } else {
                        ivPoints[i].setImageResource(R.mipmap.ic_point_unchecked);
                    }
                }
            }
        });
    }

}
