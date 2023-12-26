package com.howlstagram.testkotlinapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.howlstagram.testkotlinapp.MainActivity
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this

        binding.lifecycleOwner = this

        setObserve()

    }

    fun setObserve() {
        loginViewModel.loginbtn.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        loginViewModel.joinbtn.observe(this) {
            if (it) {
                startActivity(Intent(this, JoinActivity::class.java))
            }
        }
    }

    fun login() {
        println("login")
        loginViewModel.loginbtn.value = true
    }

    fun join() {
        println("join")
        loginViewModel.joinbtn.value = true
    }

}