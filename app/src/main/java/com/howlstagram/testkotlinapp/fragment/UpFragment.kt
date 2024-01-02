package com.howlstagram.testkotlinapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.FragmentUpBinding

class UpFragment : Fragment() {

    lateinit var binding: FragmentUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_up, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

}