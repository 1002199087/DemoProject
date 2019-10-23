package com.temporary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.temporary.demoproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dee on 2018/6/19.
 */

public class LooperDatasAdapter extends RecyclerView.Adapter<LooperDatasAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> list;
    private List<Boolean> booleans;

    public LooperDatasAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
        booleans = new ArrayList<>();
        for (String string : this.list) {
            booleans.add(false);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_looper_data_item, parent,
                false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.dataTV.setText(list.get(position));
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.looper_data_textview)
        protected TextView dataTV;
        @BindView(R.id.looper_data_checkbox)
        protected CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    booleans.set(getPosition(), b);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            checkBox.setChecked(!checkBox.isChecked());
        }
    }

    public List<Boolean> getBooleans() {
        return booleans;
    }

    public List<String> getList() {
        return this.list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
