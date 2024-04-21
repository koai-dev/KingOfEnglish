package com.koai.kingofenglish.ui.tutorial.tutorial_2

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenTutorial2Binding
import com.koai.kingofenglish.ui.tutorial.TutorialNavigator

class Tutorial2Screen : BaseScreen<ScreenTutorial2Binding, Tutorial2Router, TutorialNavigator>(R.layout.screen_tutorial_2) {
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
