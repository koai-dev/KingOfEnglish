package com.koai.kingofenglish.ui.dialog.tip

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogTipBinding
import com.koai.kingofenglish.utils.AppConfig
import com.koai.kingofenglish.utils.Constants

class TipDialog : BaseDialog<DialogTipBinding, BaseRouter, MainNavigator>(R.layout.dialog_tip) {
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
        this@TipDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogTipBinding,
    ) {
        binding.imgTip.loadImage(arguments?.getString(Constants.BASE_URL) ?: "", onFail = {
            dismiss()
            setFragmentResult(Constants.RESUME, bundleOf())
        })

        binding.btnClose.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            setFragmentResult(Constants.RESUME, bundleOf())
        }
    }
}
