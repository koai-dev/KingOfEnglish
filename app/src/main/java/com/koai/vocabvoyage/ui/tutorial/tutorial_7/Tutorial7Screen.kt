package com.koai.vocabvoyage.ui.tutorial.tutorial_7

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.ScreenTutorial7Binding
import com.koai.vocabvoyage.ui.tutorial.TutorialNavigator

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
