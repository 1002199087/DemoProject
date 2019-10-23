package com.temporary.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.temporary.bean.PeopleDao;
import com.temporary.demoproject.R;
import com.temporary.demoproject.databinding.RecyclerViewItemBinding;

import java.util.List;

/**
 * Created by wyy on 2019/3/5 0005.
 */

public class DataBindingAdapter extends RecyclerView.Adapter {
    private List<String> mItemList;
    private Context mContext;

    public DataBindingAdapter(Context context, List<String> itemList) {
        mContext = context;
        this.mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerViewItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from
                        (mContext), R.layout.recycler_view_item,
                parent, false);
        return new ItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            PeopleDao peopleDao = new PeopleDao();
            peopleDao.setName("wyy");
            RecyclerViewItemBinding binding = viewHolder.getItemBinding();
            binding.setPeople(peopleDao);
            //binding.textView.setText(String.valueOf(position));
        }
    }

    @Override
    public int getItemCount() {
        return this.mItemList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerViewItemBinding mItemBinding;

        public ItemViewHolder(RecyclerViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mItemBinding = itemBinding;
        }

        public RecyclerViewItemBinding getItemBinding() {
            return mItemBinding;
        }

        public void setItemBinding(RecyclerViewItemBinding mItemBinding) {
            this.mItemBinding = mItemBinding;
        }
    }
}
