package com.koai.vocabvoyage.ui.tutorial

import android.app.Dialog
import android.os.Bundle
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseJourneyDialog
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.JourneyTutorialBinding

class TutorialJourney :
    BaseJourneyDialog<JourneyTutorialBinding, BaseRouter, TutorialNavigator>(R.layout.journey_tutorial) {
    override val navigator: TutorialNavigator by navigatorViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.Theme_TutorialDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is TutorialNavigator.FinishTutorialEvent -> this@TutorialJourney.dismiss()
            else -> super.onNavigationEvent(event)
        }
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: JourneyTutorialBinding,
    ) {
    }
}
