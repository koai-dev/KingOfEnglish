package com.english.vocab

import android.app.Activity
import androidx.core.os.bundleOf
import com.english.vocab.ui.home.HomeRouter
import com.english.vocab.ui.leaderBoad.LeaderBoardRouter
import com.english.vocab.ui.login.LoginRouter
import com.english.vocab.ui.play.PlayRouter
import com.english.vocab.ui.splash.SplashRouter
import com.english.vocab.ui.theme.CustomThemeRouter
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.Constants
import com.english.vocab.utils.NotificationHelper
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.navigator.BaseNavigator
import com.english.vocab.ui.dialog.notification.NotificationSettingRouter
import com.english.vocab.ui.dialog.profile.ProfileRouter
import com.english.vocab.ui.dialog.setting.SettingRouter

class MainNavigator :
    BaseNavigator(),
    SplashRouter,
    LoginRouter,
    HomeRouter,
    PlayRouter,
    SettingRouter,
    ProfileRouter, CustomThemeRouter, LeaderBoardRouter, NotificationSettingRouter {

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
        offNavScreen(R.id.action_global_customThemeScreen)
    }

    override fun gotoSettingScreen() {
        offNavScreen(R.id.action_global_settingDialog)
    }

    override fun gotoTutorial() {
        offNavScreen(R.id.action_global_tutorialJourney)
    }

    override fun gotoLeaderBoardScreen() {
        offNavScreen(R.id.action_global_leaderBoardScreen)
    }

    override fun gotoProfile() {
        offNavScreen(R.id.action_global_profileDialog)
    }

    override fun gotoNotificationSetting() {
        offNavScreen(R.id.action_global_notificationSettingDialog)
    }

    override fun onPause() {
        offNavScreen(R.id.action_global_pauseDialog)
    }

    override fun gotoTip(img: String) {
        offNavScreen(R.id.action_global_tipDialog, bundleOf(Constants.BASE_URL to img))
    }

    override fun nextLevel(currentPoint: Int, img: String?) {
        offNavScreen(
            R.id.action_global_levelUpDialog,
            bundleOf(Constants.ADDED_POINTS to currentPoint, Constants.IMG to img),
        )
    }

    override fun showWrongToast() {
        offNavScreen(R.id.action_global_incorrectDialog)
    }

    override fun watchAds() {
        offNavScreen(R.id.action_global_watchAdsDialog)
    }

    override fun lose() {
        offNavScreen(R.id.action_global_loseDialog)
    }

    override fun turnOnMusic() {
        sendEvent(MusicEvent(true))
    }

    override fun turnOffMusic() {
        sendEvent(MusicEvent(false))
    }

    override fun turnOnSoundEffect() {
        sendEvent(SoundEffectEvent(true))
    }

    override fun turnOffSoundEffect() {
        sendEvent(SoundEffectEvent(false))
    }

    override fun turnOnVibrate() {
        sendEvent(VibrateEvent(true))
    }

    override fun turnOffVibrate() {
        sendEvent(VibrateEvent(false))
    }

    override fun gotoTerm() {
        offNavScreen(R.id.action_global_termScreen)
    }

    override fun gotoReport() {
        sendEvent(ReportEvent())
    }

    override fun setBackground(url: String) {
        AppConfig.background = url
    }

    override fun gotoNotificationSetting(activity: Activity) {
        NotificationHelper.requestAllowNotifyFromSetting(activity)
    }
}

class DashboardEvent : NavigationEvent()

class MusicEvent(val enable: Boolean) : NavigationEvent()

class SoundEffectEvent(val enable: Boolean) : NavigationEvent()

class VibrateEvent(val enable: Boolean) : NavigationEvent()

class ReportEvent : NavigationEvent()

class NewsEvent(val news: String) : NavigationEvent()
