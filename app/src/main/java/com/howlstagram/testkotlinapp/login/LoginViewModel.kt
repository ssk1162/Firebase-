package com.howlstagram.testkotlinapp.login

import android.app.Application
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.howlstagram.testkotlinapp.R

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var auth = FirebaseAuth.getInstance()

    // 로그인 아이디 비밀번호
    var userid: MutableLiveData<String> = MutableLiveData("")
    var userpassword: MutableLiveData<String> = MutableLiveData("")

    // 메인/회원가입
    var loginbtn: MutableLiveData<Boolean> = MutableLiveData(false)
    var joinbtn: MutableLiveData<Boolean> = MutableLiveData(false)

    // 상세 정보입력
    var InfoActivity: MutableLiveData<Boolean> = MutableLiveData(false)

    val userData = MutableLiveData<Map<String, Any>>()

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

    fun loginsign() {
        if (userid.value.toString().equals("") || userpassword.value.toString().equals("")) {
            toastMessage.value = "아이디와 비밀번호를 입력하세요"
        } else {
            auth?.signInWithEmailAndPassword(userid.value.toString(), userpassword.value.toString())
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        goMain(it.result?.user)
                        toastMessage.value = "로그인"
                    } else {
                        toastMessage.value = "로그인 실패"
                    }
                }
        }
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

    // 구글 회원가입
    fun firebaseAutWithGoogle(idToken: String?) {
        val creadential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(creadential).addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.user?.isEmailVerified == true) {
                    loginbtn.value = true
                    println("로그인")
                } else {
                    InfoActivity.value = true
                    println("상세")
                }
            }
        }
    }
    fun firebaseAuthWithFacebook(accessToken: AccessToken) {
        val creadential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(creadential).addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.user?.isEmailVerified == true) {
                    loginbtn.value = true
                    println("로그인")
                } else {
                    InfoActivity.value = true
                    println("상세")
                }
            }
        }
    }
}