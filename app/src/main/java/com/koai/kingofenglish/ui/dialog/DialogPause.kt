package com.koai.kingofenglish.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseDialog
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogLevelUpBinding
import com.koai.kingofenglish.databinding.DialogPauseBinding
import com.koai.kingofenglish.network.QuestionViewModel
import com.koai.kingofenglish.ui.play.PlayRouter
import kotlinx.coroutines.launch

class DialogPause : BaseDialog<DialogPauseBinding, PlayRouter, MainNavigator>(R.layout.dialog_pause) {
    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]

    override fun initView(savedInstanceState: Bundle?, binding: DialogPauseBinding) {

        setupsView()
        actionViews()
    }

    private fun actionViews() {
        binding.ctnBtnContinue.setClickableWithScale {
            dismiss()
        }
        binding.ctnBtnHome.setClickableWithScale {
            router?.goToHome()
        }
    }

    private fun setupsView() {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val layoutParams = WindowManager.LayoutParams()
        val window = dialog?.window
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
    }

}