package com.temporary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.temporary.bean.LogBean;
import com.temporary.bean.MeterDataBean;
import com.temporary.demoproject.R;
import com.vise.log.ViseLog;

import java.util.List;

/**
 * theme:
 * author：wyy
 */
public class LogAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<LogBean> mList;
    private LogAdapterListener mListener;

    public LogAdapter(Context context, List<LogBean> list, LogAdapterListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_log_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            LogBean bean = mList.get(position);
            if (bean.getMode() == 0)
                viewHolder.contentTV.setText(mList.get(position).getInfo());
            else {
                viewHolder.contentTV.setText(generateSp(bean));
                viewHolder.contentTV.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView contentTV;

        public ItemViewHolder(View view) {
            super(view);
            contentTV = (TextView) view.findViewById(R.id.cotent_tv);
        }
    }

    private SpannableString generateSp(LogBean bean) {
        switch (bean.getMode()) {
            case 1: {// 错误提示
                SpannableString sp = new SpannableString(bean.getInfo());
                sp.setSpan(new ForegroundColorSpan(Color.RED),
                        bean.getInfo().indexOf(bean.getKeyString()),
                        bean.getKeyString().length() + bean.getInfo().indexOf(bean.getKeyString()),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sp;
            }
            case 2: {// 表号
                SpannableString sp = new SpannableString(bean.getInfo());
                sp.setSpan(new TextClick(bean.getBean()),
                        bean.getInfo().indexOf(bean.getKeyString()),
                        bean.getKeyString().length() + bean.getInfo().indexOf(bean.getKeyString()),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sp;
            }
            case 3: {// 蓝牙
                SpannableString sp = new SpannableString(bean.getInfo());
                sp.setSpan(new TextClick(bean.getKeyString()),
                        bean.getInfo().indexOf(bean.getKeyString()),
                        bean.getKeyString().length() + bean.getInfo().indexOf(bean.getKeyString()),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sp;
            }
        }
        return null;
    }

    private class TextClick extends ClickableSpan {
        private MeterDataBean bean;
        private String keyString;

        public TextClick(String keyString) {
            this.keyString = keyString;
        }

        public TextClick(MeterDataBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(@NonNull View view) {
            if (bean != null) {
                mListener.onMeterBeanClickedListener(bean);
            } else {
                mListener.onKeyStringClickedListener(keyString);
            }
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    public interface LogAdapterListener {
        void onMeterBeanClickedListener(MeterDataBean bean);

        void onKeyStringClickedListener(String keyString);
    }
}
