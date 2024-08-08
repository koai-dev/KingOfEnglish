package com.english.vocab.domain.account

import com.english.vocab.domain.models.User
import com.facebook.login.LoginManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object AccountUtils {
    var user: User? = null

    fun isLogin() = user?.userId != null

    fun signOut() {
        Firebase.auth.signOut()
        LoginManager.getInstance().logOut()
        user = null
    }
}
