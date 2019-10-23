package com.temporary.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.temporary.demoproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dee on 2018/5/21.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private List<ScanResult> mList;
    private ViewClickListener mViewClickListener;

    public RecyclerViewAdapter(List<ScanResult> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.activity_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mBssidTextView.setText(mList.get(position).SSID);
        holder.mStateTextView.setText(mList.get(position).BSSID);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.bssid_textview)
        TextView mBssidTextView;
        @BindView(R.id.state_textview)
        TextView mStateTextView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mViewClickListener.click(getPosition());
        }
    }

    public void setViewClickListener(ViewClickListener viewClickListener) {
        this.mViewClickListener = viewClickListener;
    }

    public interface ViewClickListener {
        void click(int position);
    }
}
