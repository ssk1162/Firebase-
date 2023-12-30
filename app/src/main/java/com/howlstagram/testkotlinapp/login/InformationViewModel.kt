package com.howlstagram.testkotlinapp.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

data class FindModel(
    var email: String? = null,
    var nickname: String? = null,
    var phoneNumber: String? = null,
    var address: String? = null,
    var timestamp: String
)

class InformationViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    // 회원가입 아이디 비밀번호
    var emailid: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")

    // 정보
    var inputnickname = ""
    var inputphoneNumber = ""
    var inputaddress = ""

    // 가입 날짜
    val input = System.currentTimeMillis()
    val time = SimpleDateFormat("yyyy-MM-dd")
    val inputtimestamp = time.format(input)

    var nextPage = MutableLiveData(false)

    var InfoActivity: MutableLiveData<Boolean> = MutableLiveData(false)

    // 정보 저장
    fun saveInfo() {

        var findModel = FindModel(auth.currentUser?.email, inputnickname, inputphoneNumber, inputaddress, inputtimestamp)

        firestore.collection("findInfo").document().set(findModel).addOnCompleteListener {
            if (it.isSuccessful) {
                nextPage.value = true
                auth.currentUser?.sendEmailVerification()
            } else {

            }
        }

    }

    // 회원가입
    fun joinSign() {
        println("join")
        auth.createUserWithEmailAndPassword(emailid.value.toString(), password.value.toString())
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    InfoActivity.value = true
                }
            }
    }


}