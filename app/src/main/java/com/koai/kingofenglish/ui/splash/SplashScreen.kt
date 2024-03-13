package com.koai.kingofenglish.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenSplashBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen :
    BaseScreen<ScreenSplashBinding, SplashRouter, MainNavigator>(R.layout.screen_splash) {
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenSplashBinding,
    ) {
        binding.btnWelcome.setClickableWithScale {
            router?.goToLogin()
        }
    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}
