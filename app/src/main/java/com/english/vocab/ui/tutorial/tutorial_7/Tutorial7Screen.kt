package com.english.vocab.ui.tutorial.tutorial_7

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTutorial7Binding
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.english.vocab.ui.tutorial.TutorialNavigator

class Tutorial7Screen :
    BaseScreen<ScreenTutorial7Binding, Tutorial7Router, TutorialNavigator>(R.layout.screen_tutorial_7) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial7Binding,
    ) {
        binding.container.setOnClickListener {
            router?.onFinishTutorial()
        }
    }
}
