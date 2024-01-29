package com.howlstagram.testkotlinapp.model

data class ContentModel (
    var uid : String? = null, // 유저 시퀀스
    var itmeName : String? = null, // 아이템 이름
    var itemDetail : String? = null, // 아이템 내용
    var itemPay : String? = null, // 아이템 가격
    var itemUrl : String? = null, // 아이템 주소
    var userId : String? = null, // 유저 이메일
    var timestamp : String? = null // 업로드 시간
    
)