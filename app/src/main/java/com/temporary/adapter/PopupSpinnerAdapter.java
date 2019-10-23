package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.temporary.demoproject.R;

import java.util.List;

/**
 * Created by Dee on 2018/8/13.
 */

public class PopupSpinnerAdapter extends RecyclerView.Adapter<PopupSpinnerAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mStateList;

    private PopupSpinnerAdapterListener mListener;

    public PopupSpinnerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mStateList = list;
    }

    @Override
    public PopupSpinnerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_adapter_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PopupSpinnerAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(mStateList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.history_item_textview);
        }
    }

    public void setPopupSpinnerAdapterListener(PopupSpinnerAdapterListener listener) {
        mListener = listener;
    }

    public interface PopupSpinnerAdapterListener{
        void onClick(int position);
    }
}
