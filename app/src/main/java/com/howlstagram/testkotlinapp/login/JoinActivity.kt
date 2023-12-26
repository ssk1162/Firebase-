package com.howlstagram.testkotlinapp.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    lateinit var binding : ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)



    }
}