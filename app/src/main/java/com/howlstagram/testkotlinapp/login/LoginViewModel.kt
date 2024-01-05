package com.howlstagram.testkotlinapp.login

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    // 로그인 아이디 비밀번호 입력
    var userid: MutableLiveData<String> = MutableLiveData("")
    var userpassword: MutableLiveData<String> = MutableLiveData("")

    // 메인/회원가입 버튼
    var loginbtn: MutableLiveData<Boolean> = MutableLiveData(false)
    var joinbtn: MutableLiveData<Boolean> = MutableLiveData(false)

    var kakaoBtn: MutableLiveData<Boolean> = MutableLiveData(false)


    // 회원가입 아이디 비밀번호 입력
    var emailid: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    // 상세 정보입력
    var InfoActivity: MutableLiveData<Boolean> = MutableLiveData(false)

    var toastMessage = MutableLiveData("")


    val context = getApplication<Application>()
    var googleSignInClient: GoogleSignInClient

    init {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }


    fun goMain(user: FirebaseUser?) {
        if (user != null) {
            loginbtn.value = true
        }
    }

    fun maintain() {
        goMain(auth.currentUser)
    }

    // LoginActivity에 있는 googleLoginResult와 연결
    fun loginGoogle(view: View) {
        var i = googleSignInClient.signInIntent
        (view.context as? LoginActivity)?.googleLoginResult?.launch(i)
    }


    fun join() {
        println("join")
        joinbtn.value = true
    }


    // 회원가입
    fun joinSign() {
        if (emailid.value.equals("") || password.value.equals("")) {
            toastMessage.value = "이메일과 비밀번호를 입력하세요"
        } else if (password.value?.length!! < 0) {

        } else {
            auth.createUserWithEmailAndPassword(emailid.value.toString(), password.value.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        InfoActivity.value = true
                    } else {
                        loginsign()
                    }
                }
        }
    }


    // 로그인
    fun loginsign() {
        if (userid.value.equals("") || userpassword.value.equals("")) {
            toastMessage.value = "이메일과 비밀번호 6자리 이상을 입력하세요"
        } else {
            auth?.signInWithEmailAndPassword(userid.value.toString(), userpassword.value.toString())
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        goMain(it.result?.user)
                        toastMessage.value = "로그인"
                    } else {
                        toastMessage.value = "정보가 맞지 않습니다"
                    }
                }
        }
    }


    // 구글 회원가입 및 로그인
    fun firebaseAutWithGoogle(idToken: String?) {
        val creadential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(creadential).addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.user?.isEmailVerified == true) {
                    goMain(it.result?.user)
                    println("로그인")
                } else {
                    // 메일이 인증 되지 않으면 정보 입력창으로
                    InfoActivity.value = true
                    println("상세")
                }
            }
        }
    }



}