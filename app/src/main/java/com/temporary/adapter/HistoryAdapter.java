package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.temporary.demoproject.R;
import com.temporary.greendao.SearcherHistory;

import java.util.List;

/**
 * Created by Dee on 2018/8/9.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context mContext;
    private List<SearcherHistory> mHistoryList;

    private IHistoryAdapter mIHistoryAdapter;

    public HistoryAdapter(Context context, List<SearcherHistory> list) {
        this.mContext = context;
        this.mHistoryList = list;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_adapter_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(mHistoryList.get(position).getMHistory());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIHistoryAdapter.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }

    public List<SearcherHistory> getHistoryList() {
        return mHistoryList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.history_item_textview);
        }
    }

    public void setIHistoryAdapter(IHistoryAdapter iHistoryAdapter) {
        mIHistoryAdapter = iHistoryAdapter;
    }

    public interface IHistoryAdapter {
        void onClick(int position);
    }
}
