package com.koai.kingofenglish.ui.tutorial

import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.ui.tutorial.tutorial_1.Tutorial1Router
import com.koai.kingofenglish.ui.tutorial.tutorial_2.Tutorial2Router
import com.koai.kingofenglish.ui.tutorial.tutorial_3.Tutorial3Router
import com.koai.kingofenglish.ui.tutorial.tutorial_4.Tutorial4Router
import com.koai.kingofenglish.ui.tutorial.tutorial_5.Tutorial5Router
import com.koai.kingofenglish.ui.tutorial.tutorial_6.Tutorial6Router
import com.koai.kingofenglish.ui.tutorial.tutorial_7.Tutorial7Router

class TutorialNavigator : BaseNavigator(), Tutorial1Router, Tutorial2Router, Tutorial3Router,
    Tutorial4Router, Tutorial5Router, Tutorial6Router, Tutorial7Router {
    override fun gotoTutorial2() {
        offNavScreen(R.id.action_global_tutorial2Screen)
    }

    override fun gotoTutorial3() {
        offNavScreen(R.id.action_global_tutorial3Screen)
    }

    override fun gotoTutorial4() {
        offNavScreen(R.id.action_global_tutorial4Screen)
    }

    override fun gotoTutorial5() {
        offNavScreen(R.id.action_global_tutorial5Screen)
    }

    override fun gotoTutorial6() {
        offNavScreen(R.id.action_global_tutorial6Screen)
    }

    override fun gotoTutorial7() {
        offNavScreen(R.id.action_global_tutorial7Screen)
    }

    override fun onFinishTutorial() {
        sendEvent(FinishTutorialEvent())
    }

    class FinishTutorialEvent : NavigationEvent()
}