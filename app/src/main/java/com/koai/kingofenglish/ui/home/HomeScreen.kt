package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.visible
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.DashboardEvent
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.NewsEvent
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding
import com.koai.kingofenglish.domain.account.AccountUtils
import com.koai.kingofenglish.utils.AppConfig
import com.koai.kingofenglish.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    private val sharePreference by inject<SharePreference>()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenHomeBinding,
    ) {
        if (!AppConfig.showedWelcomeTitle) {
            AppConfig.showedWelcomeTitle = true
            binding.root.postDelayed({
                if (!AppConfig.showedLeaderBoard && !AccountUtils.isLogin()) {
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
        if (AppConfig.showedLeaderBoard) {
            binding.layoutItemDashboard.btnLeaderBoard.visible()
        } else {
            binding.layoutItemDashboard.btnLeaderBoard.gone()
        }
        if (AppConfig.background.isNullOrEmpty()) {
            binding.ctnLinkAccount.imgBg.loadImage(R.drawable.bg_home)
        } else {
            binding.ctnLinkAccount.img = AppConfig.background
        }
        binding.ctnLinkAccount.btnNo.gone()
        binding.ctnLinkAccount.btnYes.gone()
        binding.ctnLinkAccount.textView.gone()
        binding.ctnLinkAccount.textView2.gone()
    }

    private fun setFirstLaunch() {
        val isFirstLaunch = !sharePreference.getBooleanPref(Constants.IS_FIRST_LAUNCH)
        if (isFirstLaunch) {
            router?.gotoTutorial()
            sharePreference.setBooleanPref(Constants.IS_FIRST_LAUNCH, true)
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
