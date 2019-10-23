package com.temporary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.temporary.demoproject.R;

import java.util.List;


/**
 * Created by Dee on 2018/9/10 0010.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mGroupList;
    private List<List<String>> mItemList;
    private ExpandableListener mListener;

    public ExpandableAdapter(Context context, List<String> groupList, List<List<String>> itemList) {
        this.mContext = context;
        this.mGroupList = groupList;
        this.mItemList = itemList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int position) {
        return mItemList.get(position).size();
    }

    @Override
    public Object getGroup(int i) {
        return mGroupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mItemList.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.custom_expandlist_group, viewGroup, false);
            viewHolder = new GroupViewHolder();
            viewHolder.groupIcon = (ImageView) view.findViewById(R.id.expandlist_group_imageview);
            viewHolder.titleTextView = (TextView) view.findViewById(R.id.expandlist_group_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) view.getTag();
        }
        viewHolder.titleTextView.setText(mGroupList.get(i));
        viewHolder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(i);
            }
        });

        if (!b) {
            viewHolder.groupIcon.setBackgroundResource(R.drawable.custom_expandlist_group_expand_icon);
        } else {
            viewHolder.groupIcon.setBackgroundResource(R.drawable.custom_expandlist_group_collapse_icon);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ItemViewHolder itemViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.custom_expandlist_item, viewGroup,
                    false);
            itemViewHolder = new ItemViewHolder();
            itemViewHolder.itemTextView = (TextView) view.findViewById(R.id.expandlist_item_textview);
            view.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) view.getTag();
        }
        itemViewHolder.itemTextView.setText(mItemList.get(i).get(i1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupViewHolder {
        ImageView groupIcon;
        TextView titleTextView;
    }

    class ItemViewHolder {
        TextView itemTextView;
    }

    public interface ExpandableListener {
        void onClick(int position);
    }

    public void setExpandableListener(ExpandableListener listener) {
        this.mListener = listener;
    }
}
