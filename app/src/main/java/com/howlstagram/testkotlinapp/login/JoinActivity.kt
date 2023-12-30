package com.howlstagram.testkotlinapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    lateinit var binding: ActivityJoinBinding
    val informationViewModel: InformationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.infoViewModel = informationViewModel

        binding.lifecycleOwner = this

        setObserve()

    }

    fun setObserve() {
        informationViewModel.InfoActivity.observe(this) {
            if (it) {
                finish()
                startActivity(Intent(this, InformationActivity::class.java))
            }
        }

    }

}