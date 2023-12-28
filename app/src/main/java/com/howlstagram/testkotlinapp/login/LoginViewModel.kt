package com.howlstagram.testkotlinapp.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    // 로그인 아이디 비밀번호
    var userid: MutableLiveData<String> = MutableLiveData("")
    var userpassword: MutableLiveData<String> = MutableLiveData("")

    var loginbtn: MutableLiveData<Boolean> = MutableLiveData(false)
    var joinbtn: MutableLiveData<Boolean> = MutableLiveData(false)

    var toastMessage = MutableLiveData("")

    fun loginsign() {
        auth?.signInWithEmailAndPassword(userid.value.toString(), userpassword.value.toString())
            ?.addOnCompleteListener {

                if (it.isSuccessful) {
                    goMain(it.result?.user)
                } else if (userid.value == null || userpassword.value == null) {
                    toastMessage.value = "정보를 입력하세요"
                } else {
                    toastMessage.value = "정보가 맞지 않습니다"
                }
            }
    }

    fun goMain(user: FirebaseUser?) {
        if (user != null) {
            loginbtn.value = true
        }
    }


}