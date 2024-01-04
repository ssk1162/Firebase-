package com.howlstagram.testkotlinapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinBinding
    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.viewModel = loginViewModel

        binding.lifecycleOwner = this

        setObserve()

    }

    fun setObserve() {
        loginViewModel.toastMessage.observe(this) {
            if (!it.isEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.InfoActivity.observe(this) {
            if (it) {
                finish()
                startActivity(Intent(this, InformationActivity::class.java))
            }
        }

    }

}