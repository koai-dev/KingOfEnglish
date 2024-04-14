package com.koai.kingofenglish.ui.dialog.pause

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.journeyViewModel
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogPauseBinding
import com.koai.kingofenglish.ui.play.PlayViewModel

class PauseDialog :
    BaseDialog<DialogPauseBinding, BaseRouter, MainNavigator>(R.layout.dialog_pause) {
    override val navigator: MainNavigator by navigatorViewModel()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(false)
        }
    }

    override fun onStart() {
        super.onStart()
        this@PauseDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun initView(savedInstanceState: Bundle?, binding: DialogPauseBinding) {
        binding.btnHome.setClickableWithScale {
            navigator.offNavScreen(R.id.action_global_homeScreen)
            dismiss()
        }

        binding.btnResume.setClickableWithScale {

        }
    }
}