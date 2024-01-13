package com.howlstagram.testkotlinapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.howlstagram.testkotlinapp.ItemListAdapter
import com.howlstagram.testkotlinapp.ItemSearchAdapter
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.FragmentSearchBinding
import com.howlstagram.testkotlinapp.model.ContentModel

class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding

    lateinit var itemFilterAdapter : ItemSearchAdapter

    var items = arrayListOf<ContentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.searchView.setOnQueryTextListener(searchViewTextLinear)

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        itemFilterAdapter = ItemSearchAdapter(items)
        binding.searchRecyclerView.adapter = itemFilterAdapter

        return binding.root
    }

    var searchViewTextLinear : SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                itemFilterAdapter.filter.filter(s)
                Log.d("TAG","검색한 $s")
                return false
            }

        }

}