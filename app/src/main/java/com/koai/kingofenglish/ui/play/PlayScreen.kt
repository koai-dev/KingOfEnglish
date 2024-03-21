package com.koai.kingofenglish.ui.play

import android.os.Bundle
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenPlayBinding
import org.koin.core.qualifier.named

class PlayScreen : BaseScreen<ScreenPlayBinding, BaseRouter, MainNavigator>(R.layout.screen_play) {

    private val viewModel: PlayViewModel by screenViewModel()
    override fun initView(savedInstanceState: Bundle?, binding: ScreenPlayBinding) {
        viewModel.getQuestionByLevel(0)
    }

    override val navigator: MainNavigator by navigatorViewModel()
}