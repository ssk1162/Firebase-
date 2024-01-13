package com.howlstagram.testkotlinapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.databinding.ItemdetailLayoutBinding
import com.howlstagram.testkotlinapp.databinding.SearchlistLayoutBinding
import com.howlstagram.testkotlinapp.model.ContentModel

class ItemSearchAdapter(var itemlist: ArrayList<ContentModel>) :
    RecyclerView.Adapter<ItemSearchAdapter.ViewHolder>(), Filterable {

    class ViewHolder(var binding: SearchlistLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    var items = arrayListOf<ContentModel>()
    var itemFilter = ItemFilter()

    var firestore = FirebaseFirestore.getInstance()

    init {
        firestore.collection("images").addSnapshotListener { value, error ->
            for (item in value!!.documents) {
                var contentModel = item.toObject(ContentModel::class.java)
                itemlist.add(contentModel!!)
            }
            notifyDataSetChanged()
        }
    }

    init {
        items.addAll(itemlist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            SearchlistLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var contentModel = items[position]
        holder.binding.imgName2.text = contentModel.itmeName
        Glide.with(holder.itemView.context).load(contentModel.itemUrl).into(holder.binding.imgView2)

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
        return items.size
    }

    override fun getFilter(): Filter {
        return itemFilter
    }

    inner class ItemFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence?): FilterResults {
            val filterString = charSequence.toString()
            val results = FilterResults()

            val filteredList: ArrayList<ContentModel> = ArrayList<ContentModel>()

            if (filterString.trim { it < ' ' }.isEmpty()) {
                results.values = itemlist
                results.count = itemlist.size

                return results

            } else {
                for (content in itemlist) {
                    if (content.itmeName!!.contains(filterString)) {
                        filteredList.add(content)
                    }
                }
            }

            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
            items.clear()
            items.addAll(filterResults.values as ArrayList<ContentModel>)
            notifyDataSetChanged()
        }

    }


}




