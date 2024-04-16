package com.koai.kingofenglish.ui.dialog.profile

import android.os.Bundle
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogProfileBinding

class ProfileDialog : BaseDialog<DialogProfileBinding, BaseRouter, MainNavigator>(R.layout.dialog_profile) {
    override val navigator: MainNavigator by navigatorViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: DialogProfileBinding) {
        binding.imageView18
    }
}