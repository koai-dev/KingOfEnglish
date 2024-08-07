package com.english.vocab.ui.dialog.lose

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.english.vocab.MainNavigator
import com.english.vocab.R
import com.english.vocab.databinding.DialogLoseBinding
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.Constants
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog

class LoseDialog : BaseDialog<DialogLoseBinding, BaseRouter, MainNavigator>(R.layout.dialog_lose) {
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
        this@LoseDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogLoseBinding,
    ) {
        binding.btnHome.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            router?.onPopScreen()
        }

        binding.btnReplay.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            setFragmentResult(Constants.REPLAY, bundleOf())
        }
    }
}
