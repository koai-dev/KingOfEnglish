package com.english.vocab.ui.dialog.update

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.english.vocab.BuildConfig
import com.english.vocab.MainNavigator
import com.english.vocab.R
import com.english.vocab.databinding.DialogNeedUpdateBinding
import com.english.vocab.utils.AppConfig
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog

class UpdateDialog :
    BaseDialog<DialogNeedUpdateBinding, BaseRouter, MainNavigator>(R.layout.dialog_need_update) {
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
        this@UpdateDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    @SuppressLint("SetTextI18n")
    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogNeedUpdateBinding,
    ) {
        binding.apply {
            txtVersion.text = "v" + AppConfig.versionNameUpdate
            txtUpdateMessage.text = AppConfig.messageUpdate
            btnUpdate.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                    ),
                )
            }
        }

    }
}