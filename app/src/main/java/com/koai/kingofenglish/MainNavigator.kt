package com.koai.kingofenglish

import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.ui.home.HomeRouter
import com.koai.kingofenglish.ui.login.LoginRouter
import com.koai.kingofenglish.ui.play.PlayRouter
import com.koai.kingofenglish.ui.splash.SplashRouter

class MainNavigator : BaseNavigator(), SplashRouter, LoginRouter, HomeRouter, PlayRouter {
    override fun gotoLoginScreen() {
        offNavScreen(R.id.action_global_loginScreen)
    }

    override fun gotoHomeScreen() {
        offNavScreen(R.id.action_global_homeScreen)
    }

    override fun gotoPlayScreen() {
        offNavScreen(R.id.action_global_playScreen)
    }

    override fun gotoCustomThemeScreen() {

    }

    override fun gotoSettingScreen() {

    }

    override fun gotoTutorial() {
        offNavScreen(R.id.action_global_tutorialJourney)
    }

    override fun gotoLeaderBoardScreen() {

    }

    override fun onPause() {

    }

    override fun gotoHome() {

    }

    override fun gotoTip() {

    }
}

class DashboardEvent : NavigationEvent()
