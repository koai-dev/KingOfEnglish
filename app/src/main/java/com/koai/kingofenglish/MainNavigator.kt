package com.koai.kingofenglish

import android.os.Bundle
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.ui.home.HomeRouter
import com.koai.kingofenglish.ui.login.LoginRouter
import com.koai.kingofenglish.ui.play.PlayRouter
import com.koai.kingofenglish.ui.splash.SplashRouter

class MainNavigator : BaseNavigator(), SplashRouter, LoginRouter,HomeRouter,PlayRouter {
    override fun goToLogin() {
        offNavScreen(R.id.action_global_loginScreen)
    }
    override fun goToPlay(bundle: Bundle) {
        offNavScreen(R.id.action_global_playScreen,bundle)
    }
    override fun goToHome() {
        offNavScreen(R.id.action_global_homeScreen)
    }
    override fun dialogProfile(){
        offNavScreen(R.id.action_global_dialogProfile)
    }
    override fun dialogSetting(){
        offNavScreen(R.id.action_global_dialogSetting)
    }

    override fun dialogLevelUp() {
        offNavScreen(R.id.action_global_dialogLevelUp)
    }

    override fun backToHome() {
        offNavScreen(R.id.action_global_homeScreen)
    }

    override fun reloadPlay(bundle: Bundle) {
        offNavScreen(R.id.action_global_playScreen,bundle)
    }
}
