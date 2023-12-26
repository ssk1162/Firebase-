package com.howlstagram.testkotlinapp.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    // 로그인 아이디 비밀번호
    var userid : MutableLiveData<String> = MutableLiveData("")
    var userpassword : MutableLiveData<String> = MutableLiveData("")

    // 회원가입 아이디 비밀번호
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var loginbtn : MutableLiveData<Boolean> = MutableLiveData(false)
    var joinbtn : MutableLiveData<Boolean> = MutableLiveData(false)
    var Infobtn : MutableLiveData<Boolean> = MutableLiveData(false)

    // 회원가입
    fun joinSign() {
        auth.createUserWithEmailAndPassword(id.value.toString(), password.value.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {

                }
            }
    }

}