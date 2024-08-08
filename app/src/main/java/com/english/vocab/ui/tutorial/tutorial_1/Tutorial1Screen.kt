package com.english.vocab.ui.tutorial.tutorial_1

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTutorial1Binding
import com.english.vocab.ui.tutorial.TutorialNavigator
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen

class Tutorial1Screen :
    BaseScreen<ScreenTutorial1Binding, Tutorial1Router, TutorialNavigator>(R.layout.screen_tutorial_1) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial1Binding,
    ) {
        binding.container.setOnClickListener {
            router?.gotoTutorial2()
        }
    }
}
