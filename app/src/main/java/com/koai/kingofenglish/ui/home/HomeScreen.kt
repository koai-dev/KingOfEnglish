package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.DashboardEvent
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding
import com.koai.kingofenglish.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    private val sharePreference by inject<SharePreference>()
    override fun initView(savedInstanceState: Bundle?, binding: ScreenHomeBinding) {
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
        binding.layoutItemDashboard.btnPlayNow.setClickableWithScale {
            router?.gotoPlayScreen()
        }

        binding.layoutItemDashboard.btnLeaderBoard.setClickableWithScale {
            router?.gotoLeaderBoardScreen()
        }

        binding.layoutItemDashboard.btnCustom.setClickableWithScale {
            router?.gotoCustomThemeScreen()
        }

        binding.layoutItemDashboard.btnSetting.setClickableWithScale {
            router?.gotoSettingScreen()
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
}