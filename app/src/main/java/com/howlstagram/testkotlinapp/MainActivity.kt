package com.howlstagram.testkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.howlstagram.testkotlinapp.databinding.ActivityMainBinding
import com.howlstagram.testkotlinapp.fragment.HomeFragment
import com.howlstagram.testkotlinapp.fragment.SearchFragment
import com.howlstagram.testkotlinapp.fragment.UpFragment
import com.howlstagram.testkotlinapp.fragment.UserFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this

        // 실행 하자마자 보여주는 뷰
        supportFragmentManager.beginTransaction().replace(R.id.main_content, HomeFragment()).commit()

        binding.bottomNavi.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.action_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, HomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, SearchFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_up -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, UpFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.action_user -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, UserFragment()).commit()
                    return@setOnItemSelectedListener true
                }

            }

            return@setOnItemSelectedListener false

        }

    }
}