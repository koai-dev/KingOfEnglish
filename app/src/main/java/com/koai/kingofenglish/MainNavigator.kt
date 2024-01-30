package com.koai.kingofenglish

import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.ui.home.HomeRouter
import com.koai.kingofenglish.ui.login.LoginRouter
import com.koai.kingofenglish.ui.splash.SplashRouter

class MainNavigator : BaseNavigator(), SplashRouter, LoginRouter,HomeRouter {
    override fun goToLogin() {
        offNavScreen(R.id.action_global_loginScreen)
    }
    override fun goToPlay() {
        offNavScreen(R.id.action_global_playScreen)
    }
    override fun goToHome() {
        offNavScreen(R.id.action_global_homeScreen)
    }
}