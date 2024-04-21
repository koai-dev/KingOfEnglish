package com.koai.kingofenglish.ui.tutorial.tutorial_4

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenTutorial4Binding
import com.koai.kingofenglish.ui.tutorial.TutorialNavigator

class Tutorial4Screen : BaseScreen<ScreenTutorial4Binding, Tutorial4Router, TutorialNavigator>(R.layout.screen_tutorial_4) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenTutorial4Binding,
    ) {
        binding.container.setOnClickListener {
            router?.gotoTutorial5()
        }
    }
}
