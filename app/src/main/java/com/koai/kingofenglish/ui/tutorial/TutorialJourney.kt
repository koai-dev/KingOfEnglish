package com.koai.kingofenglish.ui.tutorial

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseJourneyDialog
import com.koai.kingofenglish.DashboardEvent
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.JourneyTutorialBinding

class TutorialJourney : BaseJourneyDialog<JourneyTutorialBinding, BaseRouter, TutorialNavigator>(R.layout.journey_tutorial) {
    override val navigator: TutorialNavigator by navigatorViewModel()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onStart() {
        super.onStart()
        this@TutorialJourney.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

    }

    override fun onNavigationEvent(event: NavigationEvent) {
        when(event){
            is TutorialNavigator.FinishTutorialEvent -> this@TutorialJourney.dismiss()
            else -> super.onNavigationEvent(event)
        }
    }
    override fun initView(savedInstanceState: Bundle?, binding: JourneyTutorialBinding) {
    }
}