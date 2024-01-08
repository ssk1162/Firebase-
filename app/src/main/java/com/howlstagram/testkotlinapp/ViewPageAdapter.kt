package com.howlstagram.testkotlinapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class ViewPageAdapter(var itemList: MutableList<Int>, var viewPager2: ViewPager2) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>() {

    inner class ViewPageHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)) {
        val viewitem = itemView.findViewById<ImageView>(R.id.slide_imgView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewPageHolder((parent))

    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        holder.viewitem.setImageResource(itemList[position])
        // 이미지가 처음으로 돌아가게 - 1
        if (position == (itemList.size - 1)) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private val runnable = Runnable {
        itemList.addAll(itemList)
        notifyDataSetChanged()
    }

}