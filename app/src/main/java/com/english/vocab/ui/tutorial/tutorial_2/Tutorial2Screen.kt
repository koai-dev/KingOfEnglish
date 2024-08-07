package com.english.vocab.ui.tutorial.tutorial_2

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTutorial2Binding
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.english.vocab.ui.tutorial.TutorialNavigator

class Tutorial2Screen :
    BaseScreen<ScreenTutorial2Binding, Tutorial2Router, TutorialNavigator>(R.layout.screen_tutorial_2) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial2Binding,
    ) {
        binding.container.setOnClickListener {
            router?.gotoTutorial3()
        }
    }
}
