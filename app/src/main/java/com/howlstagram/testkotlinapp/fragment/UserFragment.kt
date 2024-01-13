package com.howlstagram.testkotlinapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.FragmentUserBinding
import com.howlstagram.testkotlinapp.login.LoginActivity
import com.kakao.sdk.user.UserApiClient

data class SelectUser(var nickname: String? = null, var timestamp: String? = null)
class UserFragment : Fragment() {

    lateinit var binding: FragmentUserBinding

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        user()

        UserApiClient.instance.me { user, error ->
            binding.username.text = "${user?.kakaoAccount?.profile?.nickname}"
        }


        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.d("","실패")
                } else {
                    Log.d("","성공")
                }
            }
            startActivity(Intent(activity, LoginActivity::class.java))
            Toast.makeText(activity, "로그아웃", Toast.LENGTH_SHORT).show()
        }

        return binding.root

    }


    // 유저 데이터 가져오기
    private fun user() {
        if (auth.currentUser != null) {
            firestore.collection("findInfo").document(auth.currentUser?.uid.toString()).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val document: DocumentSnapshot = it.getResult()
                        val datauser = document.toObject(SelectUser::class.java)

                        Log.d("datauser", "${datauser?.nickname}")
                        binding.username.text = datauser?.nickname

                        Log.d("datauser", "${datauser?.timestamp}")
                        binding.usertime.text = datauser?.timestamp
                    } else {
                        Log.d("datauser", "없음")
                    }
                }
        }
    }





}