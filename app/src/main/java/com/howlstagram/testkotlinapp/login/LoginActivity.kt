package com.howlstagram.testkotlinapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.howlstagram.testkotlinapp.MainActivity
import com.howlstagram.testkotlinapp.R
import com.howlstagram.testkotlinapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = loginViewModel
        binding.activity = this

        binding.lifecycleOwner = this

        setObserve()

    }

    fun setObserve() {
        loginViewModel.toastMessage.observe(this) {
            if (!it.isEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.loginbtn.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        loginViewModel.joinbtn.observe(this) {
            if (it) {
                startActivity(Intent(this, JoinActivity::class.java))
            }
        }
        loginViewModel.InfoActivity.observe(this) {
            if (it) {
                finish()
                startActivity(Intent(this, InformationActivity::class.java))
            }
        }

    }

    fun join() {
        println("join")
        loginViewModel.joinbtn.value = true
    }

    // 로그인 유지
    override fun onStart() {
        super.onStart()
        val log = GoogleSignIn.getLastSignedInAccount(this)
        if (log !== null) {
            loginViewModel.maintain()
        }
    }

    var googleLoginResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            var data = result.data
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account = task.getResult(ApiException::class.java)
            account.idToken
            loginViewModel.firebaseAutWithGoogle(account.idToken)

        }

}