package com.english.vocab.ui.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.english.vocab.BuildConfig
import com.english.vocab.DashboardEvent
import com.english.vocab.MainNavigator
import com.english.vocab.NewsEvent
import com.english.vocab.R
import com.english.vocab.databinding.ScreenHomeBinding
import com.english.vocab.domain.account.AccountUtils
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.NotificationHelper
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.visible
import com.koai.base.main.screens.BaseScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenHomeBinding,
    ) {
        if (!AppConfig.showedWelcomeTitle) {
            AppConfig.showedWelcomeTitle = true
            binding.root.postDelayed({
                if (!AccountUtils.isLogin()) {
                    navigator.sendEvent(NewsEvent("Let's login and compete with players worldwide. 😋 "))
                } else {
                    navigator.sendEvent(NewsEvent("Let's try hard now! Good luck 😋 "))
                }
            }, 3000)
        }
        Log.d("initView", HomeScreen::class.simpleName.toString())
        setupUI()
        setAction()
        setFirstLaunch()

        if (AppConfig.versionCodeUpdate > BuildConfig.VERSION_CODE)
            {
                router?.gotoUpdate()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1200)
            navigator.sendEvent(DashboardEvent())
        }
        Log.d("onCreate", HomeScreen::class.simpleName.toString())
    }

    private fun setupUI() {
        binding.layoutItemDashboard.user = AccountUtils.user
        binding.layoutItemDashboard.btnLeaderBoard.visible()

        if (AppConfig.background.isNullOrEmpty()) {
            binding.ctnLinkAccount.imgBg.loadImage(R.drawable.bg_home)
        } else {
            binding.ctnLinkAccount.img = AppConfig.background
        }
        binding.ctnLinkAccount.btnNo.gone()
        binding.ctnLinkAccount.btnYes.gone()
        binding.ctnLinkAccount.txtPrivacy.gone()
        binding.ctnLinkAccount.textView2.gone()
    }

    private fun setFirstLaunch() {
        if (!NotificationHelper.areNotificationsEnabled(activity)) {
            if (!AppConfig.showPopupNotificationSetting) {
                router?.gotoNotificationSetting()
            }
        }
    }

    private fun setAction() {
        binding.layoutItemDashboard.imgUserTutorial.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoProfile()
        }
        binding.layoutItemDashboard.btnPlayNow.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoPlayScreen()
        }

        binding.layoutItemDashboard.btnLeaderBoard.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoLeaderBoardScreen()
        }

        binding.layoutItemDashboard.btnCustom.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoCustomThemeScreen()
        }

        binding.layoutItemDashboard.btnSetting.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoSettingScreen()
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
}
