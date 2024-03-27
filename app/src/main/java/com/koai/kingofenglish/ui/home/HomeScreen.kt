package com.koai.kingofenglish.ui.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.visible
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding
import com.koai.kingofenglish.databinding.ScreenTutorial1Binding
import com.koai.kingofenglish.utils.Constants
import org.koin.android.ext.android.inject

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    private val sharePreference by inject<SharePreference>()
    override fun initView(savedInstanceState: Bundle?, binding: ScreenHomeBinding) {
        setupUI()
        setAction()
        setFirstLaunch()
    }

    private fun setupUI() {
        binding.ctnLinkAccount.btnNo.gone()
        binding.ctnLinkAccount.btnYes.gone()
        binding.ctnLinkAccount.textView.gone()
        binding.ctnLinkAccount.textView2.gone()
    }

    private fun setFirstLaunch() {
//        val isFirstLaunch = !sharePreference.getBooleanPref(Constants.IS_FIRST_LAUNCH)
//        if (isFirstLaunch) {
        router?.gotoTutorial()
        sharePreference.setBooleanPref(Constants.IS_FIRST_LAUNCH, true)
//        }
    }

//    private fun onTutorial(step: Int) {
//        when(step){
//            0 -> binding.tutorial1Screen.root.visible()
//            else -> binding.tutorial2Screen.container.visible()
//        }
//    }

    private fun setAction() {
        binding.layoutItemDashboard.btnPlayNow.setClickableWithScale {

        }

        binding.layoutItemDashboard.btnLeaderBoard.setClickableWithScale {

        }

//        binding.tutorial1Screen.root.setOnClickListener {
//            it.gone()
//            onTutorial(1)
//        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
}