package com.howlstagram.testkotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.databinding.ActivityDetailBinding
import com.howlstagram.testkotlinapp.login.InputInfo
import com.howlstagram.testkotlinapp.model.ContentModel

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    private val TAG = "DetailActivity"

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

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


        binding.checkBtn.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                itemup()
                Toast.makeText(this, "찜", Toast.LENGTH_SHORT).show()
            } else {
                itemdel()
                Toast.makeText(this, "찜 해제", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun itemup() {
        var contentModels = ContentModel()
        contentModels.itemUrl = intent.getStringExtra("url")
        contentModels.itmeName = intent.getStringExtra("name")
        contentModels.itemDetail = intent.getStringExtra("detail")
        contentModels.itemPay = intent.getStringExtra("pay")
        contentModels.uid = auth.uid
        contentModels.userId = auth.currentUser?.email

        firestore.collection("upInfo").document(auth.currentUser?.uid.toString()).set(contentModels)
            .addOnSuccessListener {
                Log.d(TAG, "찜 성공")
            }.addOnFailureListener {
                Log.d(TAG, "찜 실패")
            }
    }

    private fun itemdel() {
        firestore.collection("upInfo").document(auth.currentUser?.uid.toString()).delete()
            .addOnSuccessListener {
                Log.d(TAG, "찜 삭제")
            }.addOnFailureListener {
                Log.d(TAG, "찜 삭제 실패")
            }
    }

}