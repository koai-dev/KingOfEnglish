package com.english.vocab.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenSplashBinding
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.english.vocab.MainNavigator
import com.english.vocab.utils.AppConfig

@SuppressLint("CustomSplashScreen")
class SplashScreen :
    BaseScreen<ScreenSplashBinding, SplashRouter, MainNavigator>(R.layout.screen_splash) {
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenSplashBinding,
    ) {
        binding.btnWelcome.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.gotoLoginScreen()
        }
        binding.root.postDelayed({
            router?.gotoLoginScreen()
        }, 1200)
    }

    override val navigator: MainNavigator by navigatorViewModel()
}
