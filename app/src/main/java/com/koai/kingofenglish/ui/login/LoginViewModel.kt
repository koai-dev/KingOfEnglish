package com.koai.kingofenglish.ui.login

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.domain.usecase.AddUserUseCase
import com.koai.kingofenglish.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val addUserUseCase: AddUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    companion object {
        val callbackManager = CallbackManager.Factory.create()
        lateinit var loginCallBack: LoginCallBack
    }

    private val _stateLogin = MutableLiveData<ResponseStatus<User>>()
    val stateLogin: LiveData<ResponseStatus<User>> = _stateLogin

    private val _user = MutableLiveData<ResponseStatus<User>>()
    val user: LiveData<ResponseStatus<User>> = _user

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

    fun login(fragment: Fragment) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateLogin.postValue(ResponseStatus.Loading)
            LoginManager.getInstance().logInWithReadPermissions(
                fragment, callbackManager, listOf("public_profile", "email")
            )
        }
    }

    fun addNewUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            addUserUseCase.execute(user).onStart {
                _stateLogin.postValue(ResponseStatus.Loading)
            }.catch {
                _stateLogin.postValue(ResponseStatus.Error(it.message.toString()))
            }.collect { userR ->
                userR.data?.let {
                    _stateLogin.postValue(ResponseStatus.Success(it))
                }
            }
        }
    }

    fun getUserInfo(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase.execute(userId).onStart {
                _user.postValue(ResponseStatus.Loading)
            }.catch {
                _user.postValue(ResponseStatus.Error(it.message.toString()))
            }.collect {
                it.data?.let { data ->
                    _user.postValue(ResponseStatus.Success(data))
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            LoginManager.getInstance().logOut()
            Firebase.auth.signOut()
        }
    }

    fun isLogin() = Firebase.auth.currentUser != null

    interface LoginCallBack {
        fun onLoginSuccess(user: User? = null)
        fun onLoginFail(message: String?)
    }
}