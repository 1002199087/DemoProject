package com.temporary.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.temporary.bean.PeopleDao;
import com.temporary.demoproject.R;
import com.temporary.demoproject.databinding.ItemPeopleBinding;
import com.vise.log.ViseLog;

import java.util.List;

public class MVVMRecyclerAdapter extends RecyclerView.Adapter {
    private Context mContext;
    public ItemPeopleBinding mBinding;
    private List<PeopleDao> mList;

    public MVVMRecyclerAdapter(Context mContext, List<PeopleDao> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_people, parent, false);
        return new ItemViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            PeopleDao dao = mList.get(position);
            ViseLog.d(dao);
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.binding.setPeople(dao);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemPeopleBinding binding;

        public ItemViewHolder(ItemPeopleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemPeopleBinding getBinding() {
            return this.binding;
        }
    }
}
