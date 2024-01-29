package com.howlstagram.testkotlinapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.howlstagram.testkotlinapp.databinding.ActivityItemBinding
import com.howlstagram.testkotlinapp.model.ContentModel
import java.text.SimpleDateFormat
import java.util.*

class ItemInputActivity : AppCompatActivity() {

    lateinit var binding: ActivityItemBinding

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance()

    var photoUri: Uri? = null
    var photoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            photoUri = it.data?.data
            binding.itemImg.setImageURI(photoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item)

        binding.lifecycleOwner = this

        binding.photoBtn.setOnClickListener {
            var i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            photoResult.launch(i)
        }

        binding.resultBtn.setOnClickListener {
            upload()
        }


    }

    fun upload() {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgName = "IMAGE_" + timestamp + ".png"
        var storagePath = storage.reference.child("images").child(imgName)

        if (photoUri != null) {
            storagePath.putFile(photoUri!!).continueWithTask {
                return@continueWithTask storagePath.downloadUrl
            }.addOnCompleteListener { downloadUrl ->
                var contentModel = ContentModel()
                contentModel.uid = auth.uid
                contentModel.itemUrl = downloadUrl.result.toString()
                contentModel.itmeName = binding.nameEd.text.toString()
                contentModel.itemDetail = binding.detailEd.text.toString()
                contentModel.itemPay = binding.payEd.text.toString()
                contentModel.timestamp = System.currentTimeMillis().toString()
                contentModel.userId = auth.currentUser?.email

                firestore.collection("images").document().set(contentModel).addOnSuccessListener {
                    Toast.makeText(this, "업로드 성공", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "업로드 실패", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }


}