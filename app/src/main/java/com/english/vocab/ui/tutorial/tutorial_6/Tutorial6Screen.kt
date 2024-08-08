package com.english.vocab.ui.tutorial.tutorial_6

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenTutorial6Binding
import com.english.vocab.ui.tutorial.TutorialNavigator
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen

class Tutorial6Screen :
    BaseScreen<ScreenTutorial6Binding, Tutorial6Router, TutorialNavigator>(R.layout.screen_tutorial_6) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial6Binding,
    ) {
        binding.container.setOnClickListener {
            router?.gotoTutorial7()
        }
    }
}
