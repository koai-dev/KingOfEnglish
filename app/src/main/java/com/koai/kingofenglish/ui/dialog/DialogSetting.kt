package com.koai.kingofenglish.ui.dialog

import android.graphics.Paint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogSettingBinding


class DialogSetting : BaseDialog<DialogSettingBinding, BaseRouter, MainNavigator>(R.layout.dialog_setting) {
    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]

    override fun initView(savedInstanceState: Bundle?, binding: DialogSettingBinding) {

        setupsView()
        actionViews()
    }

    private fun actionViews() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setupsView() {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding.txtTerm.paintFlags =  binding.txtTerm.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.txtPrivacyPolicy.paintFlags =  binding.txtPrivacyPolicy.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

}
