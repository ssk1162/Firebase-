package com.howlstagram.testkotlinapp

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.native_app_key))

//        val key = Utility.getKeyHash(this)
//        Log.d("key : ","$key")

    }

}