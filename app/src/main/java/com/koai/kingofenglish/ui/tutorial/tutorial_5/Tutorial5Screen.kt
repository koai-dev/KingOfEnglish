package com.koai.kingofenglish.ui.tutorial.tutorial_5

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenTutorial3Binding
import com.koai.kingofenglish.databinding.ScreenTutorial5Binding
import com.koai.kingofenglish.ui.tutorial.TutorialNavigator

class Tutorial5Screen :
    BaseScreen<ScreenTutorial5Binding, Tutorial5Router, TutorialNavigator>(R.layout.screen_tutorial_5) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenTutorial5Binding) {
        binding.container.setOnClickListener {
            router?.gotoTutorial6()
        }
    }
}