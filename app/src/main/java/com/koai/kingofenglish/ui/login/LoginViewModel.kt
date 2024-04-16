package com.koai.kingofenglish.ui.login

import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginFragment
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koai.kingofenglish.domain.models.User

class LoginViewModel : ViewModel() {
    companion object {
        val callbackManager = CallbackManager.Factory.create()
        lateinit var loginCallBack: LoginCallBack
    }

    init {
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    loginCallBack.onLoginFail("Cancel")
                }

                override fun onError(error: FacebookException) {
                    loginCallBack.onLoginFail(message = error.message)
                }

                override fun onSuccess(result: LoginResult) {
                    val accessToken = AccessToken.getCurrentAccessToken()
                    if (accessToken != null && !accessToken.isExpired) {
                        val credential = FacebookAuthProvider.getCredential(accessToken.token)
                        Firebase.auth.currentUser?.linkWithCredential(credential)
                        Firebase.auth.signInWithCredential(credential)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    loginCallBack.onLoginSuccess()
                                } else {
                                    loginCallBack.onLoginFail(
                                        task.exception?.message ?: "Unknown Error!"
                                    )
                                }
                            }
                    }
                }

            })
    }

    fun login(fragment: LoginFragment) {
        LoginManager.getInstance().logInWithReadPermissions(
            fragment, callbackManager, listOf("public_profile", "email")
        )
    }

    fun createOrGetUserInfo(){

    }

    fun logout() {
        LoginManager.getInstance().logOut()
        Firebase.auth.signOut()
    }

    interface LoginCallBack {
        fun onLoginSuccess(user: User? = null)
        fun onLoginFail(message: String?)
    }
}