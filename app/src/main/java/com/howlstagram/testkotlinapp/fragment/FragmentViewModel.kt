package com.howlstagram.testkotlinapp.fragment

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()
    var firestore = FirebaseFirestore.getInstance()



}

