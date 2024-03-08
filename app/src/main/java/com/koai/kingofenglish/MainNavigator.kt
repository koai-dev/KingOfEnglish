package com.koai.kingofenglish

import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.ui.login.LoginRouter
import com.koai.kingofenglish.ui.splash.SplashRouter

class MainNavigator : BaseNavigator(), SplashRouter, LoginRouter {
    override fun goToHome() {
        offNavScreen(R.id.action_global_loginScreen)
    }
}
