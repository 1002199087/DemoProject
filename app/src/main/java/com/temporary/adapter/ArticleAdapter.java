package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.temporary.custom.Article;
import com.temporary.custom.group.GroupRecyclerAdapter;
import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 适配器
 * Created by huanghaibin on 2017/12/4.
 */

public class ArticleAdapter extends GroupRecyclerAdapter<String, Article> {


    private RequestManager mLoader;

    public ArticleAdapter(Context context) {
        super(context);
        mLoader = Glide.with(context.getApplicationContext());
        LinkedHashMap<String, List<Article>> map = new LinkedHashMap<>();
        List<String> titles = new ArrayList<>();
        map.put("今日推荐", create(0));
        map.put("每周热点", create(1));
        map.put("最高评论", create(2));
        titles.add("");
        titles.add("");
        titles.add("");
        resetGroups(map, titles);
    }

    public ArticleAdapter(Context context, List<String> titleList) {
        super(context);
        mLoader = Glide.with(context.getApplicationContext());
        LinkedHashMap<String, List<Article>> map = new LinkedHashMap<>();
        List<String> titles = new ArrayList<>();
        map.put("今日推荐", create(0));
        map.put("每周热点", create(1));
        map.put("最高评论", create(2));
        titles.add("");
        titles.add("");
        titles.add("");
        resetGroups(map, titles);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new ArticleViewHolder(mInflater.inflate(R.layout.item_list_article, parent, false));
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
        ArticleViewHolder h = (ArticleViewHolder) holder;
        h.mTextTitle.setText(item.getTitle());
        h.mTextContent.setText(item.getContent());
        /*mLoader.load(item.getImgUrl())
                .asBitmap()
                .centerCrop()
                .into(h.mImageView);*/
        Log.e("wyy", "ArticleAdapter onBindViewHolder " + position);
    }

    private static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextTitle,
                mTextContent;
        //private ImageView mImageView;

        private ArticleViewHolder(View itemView) {
            super(itemView);
            mTextTitle = (TextView) itemView.findViewById(R.id.daily_title);
            mTextContent = (TextView) itemView.findViewById(R.id.daily_content);
            //mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


    private static Article create(String title, String content, String imgUrl) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setImgUrl(imgUrl);
        return article;
    }

    private static List<Article> create(int p) {
        List<Article> list = new ArrayList<>();
        if (p == 0) {
            list.add(create("今日完成工作：",
                    "#地震快讯#中国地震台网正式测定：12月04日08时08分在克马德克群岛（南纬32.82度，西经178.73度）发生5.7级地震，震源深度10千米。",
                    "http://cms-bucket.nosdn.127.net/catchpic/2/27/27e2ce7fd02e6c096e21b1689a8a3fe9.jpg?imageView&thumbnail=550x0"));
        } else if (p == 1) {
            list.add(create(
                    "今日完成工作：",
                    "网易汽车11月30日报道 两周前的广州车展上，电咖发布了其首款电动汽车EV10，官方指导价为13.38万-14.18万，扣除补贴后的零售价为5.98万元-6.78万元，性价比很高。抛开车辆本身，引起业界关注的是这家新势力造车企业的几位核心成员，当年上汽大众团队的三位老兵--张海亮、向东平、牛胜福携手用了957天造了一辆可以上市的车。",
                    "http://cms-bucket.nosdn.127.net/674c392123254bb69bdd9227442965eb20171129203658.jpeg?imageView&thumbnail=550x0"));
        } else if (p == 2) {
            list.add(create(
                    "今日完成工作：",
                    "网易汽车11月30日报道 两周前的广州车展上，电咖发布了其首款电动汽车EV10，官方指导价为13.38万-14.18万，扣除补贴后的零售价为5.98万元-6.78万元，性价比很高。抛开车辆本身，引起业界关注的是这家新势力造车企业的几位核心成员，当年上汽大众团队的三位老兵--张海亮、向东平、牛胜福携手用了957天造了一辆可以上市的车。",
                    "http://cms-bucket.nosdn.127.net/674c392123254bb69bdd9227442965eb20171129203658.jpeg?imageView&thumbnail=550x0"));
        }
        return list;
    }
}
