package com.english.vocab.ui.tutorial

import android.app.Dialog
import android.os.Bundle
import com.english.vocab.MainNavigator
import com.english.vocab.MainViewModel
import com.english.vocab.R
import com.english.vocab.databinding.JourneyTutorialBinding
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.journeyViewModel
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseJourneyDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class TutorialJourney :
    BaseJourneyDialog<JourneyTutorialBinding, BaseRouter, TutorialNavigator>(R.layout.journey_tutorial) {
    override val navigator: TutorialNavigator by navigatorViewModel()
    private val mainNavigator: MainNavigator by navigatorViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NORMAL, R.style.Theme_TutorialDialog)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is TutorialNavigator.FinishTutorialEvent -> {
                this@TutorialJourney.dismiss()
                mainNavigator.finishTutorial()
            }
            else -> super.onNavigationEvent(event)
        }
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: JourneyTutorialBinding,
    ) {
    }
}
