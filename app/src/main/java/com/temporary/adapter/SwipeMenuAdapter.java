package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.temporary.demoproject.R;

import java.util.List;

/**
 * Created by Dee on 2018/8/9.
 */

public class SwipeMenuAdapter extends RecyclerView.Adapter<SwipeMenuAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mList;

    private ISwipeMenuAdapter mISwipeMenuAdapter;

    public SwipeMenuAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public SwipeMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.swipe_menu_adapter_item,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SwipeMenuAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(mList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mISwipeMenuAdapter.onItemClick(position);
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.swipeMenuLayout.smoothClose();
                mISwipeMenuAdapter.onDelClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<String> getList() {
        return mList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button delBtn;
        SwipeMenuLayout swipeMenuLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.demo_textview);
            delBtn = (Button) itemView.findViewById(R.id.del_button);
            swipeMenuLayout = (SwipeMenuLayout) itemView.findViewById(R.id.swipe_menu_layout);
        }
    }

    public void setISwipeMenuAdapter(ISwipeMenuAdapter iSwipeMenuAdapter) {
        this.mISwipeMenuAdapter = iSwipeMenuAdapter;
    }

    public interface ISwipeMenuAdapter {
        void onItemClick(int position);

        void onDelClick(int position);
    }
}
