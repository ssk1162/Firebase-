package com.howlstagram.testkotlinapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.FragmentUserBinding
import com.howlstagram.testkotlinapp.login.LoginActivity

class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding
    val fragmentViewModel: FragmentViewModel by viewModels()

    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        binding.viewModel = fragmentViewModel
        binding.lifecycleOwner = this


        binding.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(activity, "로그아웃", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, LoginActivity::class.java))
        }


        return binding.root

    }


}