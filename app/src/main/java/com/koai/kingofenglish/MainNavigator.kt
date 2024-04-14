package com.koai.kingofenglish

import androidx.core.os.bundleOf
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.ui.home.HomeRouter
import com.koai.kingofenglish.ui.login.LoginRouter
import com.koai.kingofenglish.ui.play.PlayRouter
import com.koai.kingofenglish.ui.splash.SplashRouter
import com.koai.kingofenglish.utils.Constants

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
        offNavScreen(R.id.action_global_pauseDialog)
    }

    override fun gotoTip(img: String) {
        offNavScreen(R.id.action_global_tipDialog, bundleOf(Constants.BASE_URL to img))
    }

    override fun nextLevel(currentPoint: Int) {
        offNavScreen(R.id.action_global_levelUpDialog, bundleOf(Constants.ADDED_POINTS to currentPoint))
    }

    override fun showWrongToast() {
        offNavScreen(R.id.action_global_incorrectDialog)
    }

    override fun watchAds() {
        offNavScreen(R.id.action_global_watchAdsDialog)
    }
}

class DashboardEvent : NavigationEvent()
