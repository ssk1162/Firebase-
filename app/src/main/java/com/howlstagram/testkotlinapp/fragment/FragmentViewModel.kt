package com.howlstagram.testkotlinapp.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.model.FindModel

class FragmentViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    val userdata = auth.currentUser

    init {
        user()
    }

    fun user() {
        Log.d("", "${userdata?.uid}")

        // 사용자가 로그인되어 있지 않은 경우 예외 처리
        if (userdata == null) {
            Log.e("", "사용자가 로그인되어 있지 않습니다.")
            return
        }

        firestore.collection("findInfo").document(userdata.uid).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                var datauser = documentSnapshot.toObject(FindModel::class.java)
                Log.d("datauser", "${datauser?.nickname}")
            } else {
                Log.d("datauser", "없음")
            }
        }.addOnFailureListener { e ->
            Log.e("datauser", "데이터 가져오기 실패", e)
        }
    }

}

