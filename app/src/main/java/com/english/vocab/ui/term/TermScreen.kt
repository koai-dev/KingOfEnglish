package com.english.vocab.ui.term

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import com.english.vocab.MainNavigator
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTermBinding
import com.english.vocab.utils.AppConfig
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen

class TermScreen : BaseScreen<ScreenTermBinding, BaseRouter, MainNavigator>(R.layout.screen_term) {
    override val navigator: MainNavigator by navigatorViewModel()

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTermBinding,
    ) {
        binding.root.post {
            binding.btnClose.setWidthHeight(binding.root.measuredHeight)
        }
        binding.web.apply {
            settings.apply {
                webViewClient = WebViewClient()
                javaScriptEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
            }
            loadUrl("https://sites.google.com/view/koaidev-privacypolicy/")
        }
        binding.btnClose.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.onPopScreen()
        }
    }
}
