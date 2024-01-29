package com.howlstagram.testkotlinapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.databinding.ItemLayoutBinding
import com.howlstagram.testkotlinapp.model.ContentModel

class ViewPageAdapter(var viewPager2: ViewPager2) :
    RecyclerView.Adapter<ViewPageAdapter.ViewPageHolder>() {

    var firestore = FirebaseFirestore.getInstance()
    var contentModels = arrayListOf<ContentModel>()

    inner class ViewPageHolder(var binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    init {
        firestore.collection("images").addSnapshotListener { value, error ->
            for (item in value!!.documents) {
                var contentModel = item.toObject(ContentModel::class.java)
                contentModels.add(contentModel!!)
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageHolder {
        var view = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPageHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPageHolder, position: Int) {
        var contentModel = contentModels[position]
        // 이미지가 처음으로 돌아가게 - 1
        if (position == (contentModels.size - 1)) {
            viewPager2.post(runnable)
        }
        Glide.with(holder.itemView.context).load(contentModel.itemUrl).into(holder.binding.slideImgView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("url", "${contentModel.itemUrl}")
            intent.putExtra("name", "${contentModel.itmeName}")
            intent.putExtra("detail", "${contentModel.itemDetail}")
            intent.putExtra("pay", "${contentModel.itemPay}")
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }

    }

    override fun getItemCount(): Int {
        return contentModels.size
    }

    private val runnable = Runnable {
        contentModels.addAll(contentModels)
        notifyDataSetChanged()
    }

}