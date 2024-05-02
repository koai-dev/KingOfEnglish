package com.koai.kingofenglish.ui.login

import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.NewsEvent
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenLoginBinding
import com.koai.kingofenglish.domain.account.AccountUtils
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.utils.AppConfig

class LoginScreen :
    BaseScreen<ScreenLoginBinding, LoginRouter, MainNavigator>(R.layout.screen_login),
    LoginViewModel.LoginCallBack {
    private val viewModel: LoginViewModel by screenViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenLoginBinding,
    ) {
        LoginViewModel.loginCallBack = this
        if (AppConfig.background.isNullOrEmpty()) {
            binding.ctnLinkAccount.imgBg.loadImage(R.drawable.bg_home)
        } else {
            binding.ctnLinkAccount.img = AppConfig.background
        }
        checkAuthStatus()
        actionView()
        observer()
    }

    private fun checkAuthStatus() {
        if (viewModel.isLogin()) {
            Firebase.auth.currentUser?.uid?.let { viewModel.getUserInfo(it) }
        }
    }

    private fun observer() {
        viewModel.stateLogin.observe(this) {
            if (it is ResponseStatus.Success) {
                if (it.data.userId != null) {
                    AccountUtils.user = it.data
                    router?.gotoHomeScreen()
                }
            }
        }

        viewModel.user.observe(this) {
            if (it is ResponseStatus.Success) {
                AccountUtils.user =
                    it.data.apply {
                        if ((currentLevel ?: 0) <= 0) {
                            currentLevel = 1
                        }
                    }
                router?.gotoHomeScreen()
            }
        }
    }

    private fun actionView() {
        binding.ctnLinkAccount.btnNo.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            viewModel.getUserInfoOffline()
        }

        binding.ctnLinkAccount.btnYes.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            viewModel.login(this)
        }

        binding.ctnLinkAccount.txtPrivacy.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoTerm()
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()

    override fun onLoginSuccess(user: User?) {
        user?.let {
            viewModel.addNewUser(it)
        }
    }

    override fun onLoginFail(message: String?) {
    }
}
