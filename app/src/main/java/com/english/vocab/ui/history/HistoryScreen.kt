package com.english.vocab.ui.history

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenHistoryBinding
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen

class HistoryScreen : BaseScreen<ScreenHistoryBinding, BaseRouter, BaseNavigator>(R.layout.screen_history) {
    override val navigator: BaseNavigator by navigatorViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenHistoryBinding) {

    }
}