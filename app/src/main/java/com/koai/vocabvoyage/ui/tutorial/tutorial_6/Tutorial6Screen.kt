package com.koai.vocabvoyage.ui.tutorial.tutorial_6

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.ScreenTutorial6Binding
import com.koai.vocabvoyage.ui.tutorial.TutorialNavigator

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
