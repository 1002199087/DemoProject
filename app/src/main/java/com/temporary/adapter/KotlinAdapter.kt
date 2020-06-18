package com.temporary.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.temporary.bean.KotlinDao
import com.temporary.demoproject.R
import kotlinx.android.synthetic.main.item_kotlin.view.*

class KotlinAdapter(var mContext: Context, var mList: List<KotlinDao>) : RecyclerView
.Adapter<RecyclerView
.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.item_kotlin, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder != null && holder is ItemViewHolder) {
            var itemView: ItemViewHolder = holder
        }
    }

    open class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleText: TextView = itemView.findViewById(R.id.title_text)
        var contentText: TextView = itemView.findViewById(R.id.content_text)
    }
}