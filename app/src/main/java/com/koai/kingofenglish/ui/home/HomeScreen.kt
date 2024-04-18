package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.journeyViewModel
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.DashboardEvent
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.MainViewModel
import com.koai.kingofenglish.R
import com.koai.kingofenglish.ads.AdsViewModel
import com.koai.kingofenglish.databinding.ScreenHomeBinding
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.ui.play.PlayViewModel
import com.koai.kingofenglish.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    private val sharePreference by inject<SharePreference>()
    private val playViewModel: PlayViewModel by screenViewModel()
    private val mainViewModel: MainViewModel by journeyViewModel()
    private val adsViewModel: AdsViewModel by journeyViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenHomeBinding) {
        Log.d("initView", HomeScreen::class.simpleName.toString())
        playViewModel.getCurrentState()
        setupUI()
        setAction()
        setFirstLaunch()
        observer()
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
        binding.layoutItemDashboard.imgUserTutorial.setClickableWithScale {
            router?.gotoProfile()
        }
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

    private fun observer() {
        val data = playViewModel.currentLevelLive.asFlow()
            .combine(playViewModel.currentPointLive.asFlow()) { level, point ->
                User(currentLevel = level, points = point)
            }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                data.collectLatest { user ->
                    binding.layoutItemDashboard.user = user
                }
            }
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
}