package com.howlstagram.testkotlinapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.databinding.ItemdetailLayoutBinding
import com.howlstagram.testkotlinapp.model.ContentModel

class UpAdpater : RecyclerView.Adapter<UpAdpater.ViewHodelr>() {

    inner class ViewHodelr(var binding: ItemdetailLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    var firestore = FirebaseFirestore.getInstance()
    var contentModels = arrayListOf<ContentModel>()


    init {
        firestore.collection("upInfo").addSnapshotListener { value, error ->
            for (item in value!!.documents) {
                var contentModel = item.toObject(ContentModel::class.java)
                contentModels.add(contentModel!!)
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodelr {
        var view =
            ItemdetailLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHodelr(view)
    }

    override fun onBindViewHolder(holder: ViewHodelr, position: Int) {
        var contentModel = contentModels[position]
        holder.binding.imgName.text = contentModel.itmeName
        Glide.with(holder.itemView.context).load(contentModel.itemUrl).into(holder.binding.imgView)

        Log.d("itmeName : ", "${contentModel.itmeName}")

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

}