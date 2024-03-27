package com.koai.kingofenglish

import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.ui.home.HomeRouter
import com.koai.kingofenglish.ui.login.LoginRouter
import com.koai.kingofenglish.ui.splash.SplashRouter

class MainNavigator : BaseNavigator(), SplashRouter, LoginRouter, HomeRouter {
    override fun goToHome() {
        offNavScreen(R.id.action_global_loginScreen)
    }

    override fun gotoHomeScreen() {
        offNavScreen(R.id.action_global_homeScreen)
    }

    override fun gotoPlay() {

    }

    override fun gotoCustomTheme() {

    }

    override fun gotoSetting() {

    }

    override fun gotoTutorial() {
        offNavScreen(R.id.action_global_tutorialJourney)
    }
}
