package com.howlstagram.testkotlinapp.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

data class InputInfo(

    var email: String? = null,
    var nickname: String? = null,
    var phoneNumber: String? = null,
    var address: String? = null,
    var timestamp: String

)

class InformationViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()

    // 정보
    var inputnickname = ""
    var inputphoneNumber = ""
    var inputaddress = ""

    // 가입 날짜
    val input = System.currentTimeMillis()
    val time = SimpleDateFormat("yyyy-MM-dd")
    val inputtimestamp = time.format(input)

    var toastMessage = MutableLiveData("")

    var nextPage = MutableLiveData(false)

    var InfoActivity: MutableLiveData<Boolean> = MutableLiveData(false)


    // 정보 저장
    fun saveInfo() {
        var inputInfo = InputInfo(
            auth.currentUser?.email,
            inputnickname,
            inputphoneNumber,
            inputaddress,
            inputtimestamp
        )

        if (inputnickname.equals("") || inputaddress.equals("") || inputphoneNumber.equals("")) {
            toastMessage.value = "정보를 입력하세요"
        } else {
            // document 미입력 시 uid가 랜덤으로 들어가서 유저 데이터 찾을 때 찾을 수 없음
            firestore.collection("findInfo").document(auth.currentUser?.uid.toString())
                .set(inputInfo)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        nextPage.value = true
                    }
                }
        }
    }




}