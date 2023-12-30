package com.howlstagram.testkotlinapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.FragmentUserBinding
import com.howlstagram.testkotlinapp.login.FindModel

class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        firestore.collection("findInfo").document().get().addOnCompleteListener {

        }

        return binding.root

    }


}