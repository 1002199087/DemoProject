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
 * Created by Dee on 2018/5/23.
 */

public class FlashairAdapter extends RecyclerView.Adapter<FlashairAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mFileList;
    public IFlashairClickListener iFlashairClickListener;

    public FlashairAdapter(List<String> list, Context context) {
        this.mFileList = list;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_flashair_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mFileTextView.setText(mFileList.get(position));
    }


    @Override
    public int getItemCount() {
        return mFileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mFileTextView;

        public MyViewHolder(View view) {
            super(view);
            mFileTextView = (TextView) view.findViewById(R.id.flashair_file_textview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iFlashairClickListener.click(getPosition());
        }
    }

    public void setFlashairClickListener(IFlashairClickListener listener) {
        this.iFlashairClickListener = listener;
    }

    public interface IFlashairClickListener{
        void click(int position);
    }
}
