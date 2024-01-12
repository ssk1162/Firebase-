package com.howlstagram.testkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.howlstagram.testkotlinapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val imageUrl = intent.getStringExtra("url")
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(binding.imgDeView)
        }
        binding.imgDeName.text = intent.getStringExtra("name")
        binding.imgDeDetail.text = intent.getStringExtra("detail")
        binding.imgDePay.text = intent.getStringExtra("pay") + " 원"

    }
}