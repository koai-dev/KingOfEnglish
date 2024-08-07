package com.english.vocab.ui.tutorial.tutorial_5

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTutorial5Binding
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.english.vocab.ui.tutorial.TutorialNavigator

class Tutorial5Screen :
    BaseScreen<ScreenTutorial5Binding, Tutorial5Router, TutorialNavigator>(R.layout.screen_tutorial_5) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial5Binding,
    ) {
        binding.container.setOnClickListener {
            router?.gotoTutorial6()
        }
    }
}
