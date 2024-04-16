package com.koai.kingofenglish.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenLoginBinding
import com.koai.kingofenglish.domain.models.User

class LoginScreen :
    BaseScreen<ScreenLoginBinding, LoginRouter, MainNavigator>(R.layout.screen_login),
    LoginViewModel.LoginCallBack {
    private val viewModel: LoginViewModel by screenViewModel()
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenLoginBinding,
    ) {
        LoginViewModel.loginCallBack = this
        actionView()
        observer()
    }

    private fun observer() {
        viewModel.stateLogin.observe(this){

        }
    }

    private fun actionView() {
        binding.ctnLinkAccount.btnNo.setClickableWithScale {
            router?.gotoHomeScreen()
        }

        binding.ctnLinkAccount.btnYes.setClickableWithScale {
            viewModel.login(this)
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
    override fun onLoginSuccess(user: User?) {
        user?.let { viewModel.addNewUser(it) }
    }

    override fun onLoginFail(message: String?) {
        //todo
    }
}
