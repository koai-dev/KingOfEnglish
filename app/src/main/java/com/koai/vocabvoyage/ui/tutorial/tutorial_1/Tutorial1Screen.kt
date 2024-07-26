package com.koai.vocabvoyage.ui.tutorial.tutorial_1

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.ScreenTutorial1Binding
import com.koai.vocabvoyage.ui.tutorial.TutorialNavigator

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
