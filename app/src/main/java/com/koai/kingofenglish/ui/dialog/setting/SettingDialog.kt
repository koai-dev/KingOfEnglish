package com.koai.kingofenglish.ui.dialog.setting

import android.os.Bundle
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogSettingBinding

class SettingDialog : BaseDialog<DialogSettingBinding, BaseRouter, MainNavigator>(R.layout.dialog_setting) {
    override val navigator: MainNavigator by navigatorViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: DialogSettingBinding) {

    }
}