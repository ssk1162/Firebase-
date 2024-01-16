package com.howlstagram.testkotlinapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.howlstagram.testkotlinapp.databinding.ItemLayoutBinding

class ViewPageAdapter(var itemList: MutableList<Int>, var viewPager2: ViewPager2) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>() {

    inner class ViewPageHolder(var binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageHolder {
        var view = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPageHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        holder.binding.slideImgView.setImageResource(itemList[position])
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