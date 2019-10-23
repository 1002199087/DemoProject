package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.temporary.bean.ExpressageData;
import com.temporary.demoproject.R;

import java.util.List;

/**
 * Created by Dee on 2018/9/3 0003.
 */

public class ExpressageAdapter extends RecyclerView.Adapter<ExpressageAdapter.MyViewHolder> {
    private Context mContext;
    private List<ExpressageData> mExpressList;

    public ExpressageAdapter(Context context, List<ExpressageData> list) {
        this.mContext = context;
        this.mExpressList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_expressage_item,
                parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.timeTV.setText(mExpressList.get(position).getTime());
        holder.contentTV.setText(mExpressList.get(position).getContext());
    }

    @Override
    public int getItemCount() {
        return mExpressList.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        TextView timeTV;
        TextView contentTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeTV = (TextView) itemView.findViewById(R.id.time_textview);
            contentTV = (TextView) itemView.findViewById(R.id.content_textview);
        }
    }
}
