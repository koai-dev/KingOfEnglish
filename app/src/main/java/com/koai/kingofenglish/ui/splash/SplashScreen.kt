package com.koai.kingofenglish.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenSplashBinding
import com.koai.kingofenglish.ui.login.LoginViewModel

@SuppressLint("CustomSplashScreen")
class SplashScreen :
    BaseScreen<ScreenSplashBinding, SplashRouter, MainNavigator>(R.layout.screen_splash) {
    private val viewModel: LoginViewModel by screenViewModel()
    override fun onStart() {
        super.onStart()
        if (viewModel.isLogin()) {
            router?.gotoHomeScreen()
        }
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenSplashBinding,
    ) {
        binding.btnWelcome.setClickableWithScale {
            router?.gotoLoginScreen()
        }
        binding.root.postDelayed({ router?.gotoLoginScreen() }, 1200)
    }

    override val navigator: MainNavigator by navigatorViewModel()
}
