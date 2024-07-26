package com.koai.vocabvoyage.ui.history

import android.os.Bundle
import com.koai.vocabvoyage.R
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.vocabvoyage.databinding.ScreenHistoryBinding

class HistoryScreen : BaseScreen<ScreenHistoryBinding, BaseRouter, BaseNavigator>(R.layout.screen_history) {
    override val navigator: BaseNavigator by navigatorViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenHistoryBinding) {
        TODO("Not yet implemented")
    }
}