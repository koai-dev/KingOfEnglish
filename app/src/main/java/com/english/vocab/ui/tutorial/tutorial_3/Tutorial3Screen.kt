package com.english.vocab.ui.tutorial.tutorial_3

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTutorial3Binding
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.english.vocab.ui.tutorial.TutorialNavigator

class Tutorial3Screen :
    BaseScreen<ScreenTutorial3Binding, Tutorial3Router, TutorialNavigator>(R.layout.screen_tutorial_3) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial3Binding,
    ) {
        binding.container.setOnClickListener {
            router?.gotoTutorial4()
        }
    }
}
