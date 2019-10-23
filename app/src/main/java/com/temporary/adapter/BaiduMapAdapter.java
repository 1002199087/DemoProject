package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.temporary.demoproject.R;

import java.util.List;

/**
 * Created by Dee on 2018/8/7.
 */

public class BaiduMapAdapter extends RecyclerView.Adapter<BaiduMapAdapter.MyViewHolder> {
    private Context mContext;
    private List<LatLng> latLngs;

    private OnItemClickListener mOnItemClickListener;

    public BaiduMapAdapter(Context context, List<LatLng> latLngs) {
        this.mContext = context;
        this.latLngs = latLngs;
    }

    @Override
    public BaiduMapAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.baidumap_adapter_item,
                parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BaiduMapAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText("纬度 : "+latLngs.get(position).latitude
                + " , 经度 : " + latLngs.get(position).longitude);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.click(position);
            }
        });
    }

    public List<LatLng> getLatLngs() {
        return this.latLngs;
    }

    @Override
    public int getItemCount() {
        return latLngs.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.baidumap_item_textview);
            view = itemView;
        }

    }

    public interface OnItemClickListener {
        void click(int position);
    }

    public void setOnItemClick(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
