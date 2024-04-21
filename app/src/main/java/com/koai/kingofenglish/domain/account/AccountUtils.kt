package com.koai.kingofenglish.domain.account

import com.facebook.login.LoginManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koai.kingofenglish.domain.models.User

object AccountUtils {
    var user: User? = null
    fun isLogin() = user?.userId != null

    fun signOut(){
        Firebase.auth.signOut()
        LoginManager.getInstance().logOut()
        user = null
    }

}