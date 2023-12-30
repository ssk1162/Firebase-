package com.howlstagram.testkotlinapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.howlstagram.testkotlinapp.MainActivity
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {

    lateinit var binding : ActivityInformationBinding
    val informationViewModel : InformationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_information)
        binding.infoViewModel = informationViewModel
        binding.lifecycleOwner = this

        setObserve()

    }

    fun setObserve() {
        informationViewModel.nextPage.observe(this) {
            if (it) {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

}