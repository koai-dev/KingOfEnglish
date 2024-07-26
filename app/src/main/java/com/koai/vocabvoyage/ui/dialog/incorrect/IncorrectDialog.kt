package com.koai.vocabvoyage.ui.dialog.incorrect

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.lifecycleScope
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.DialogIncorrectBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IncorrectDialog : BaseDialog<DialogIncorrectBinding, BaseRouter, BaseNavigator>(R.layout.dialog_incorrect) {
    override val navigator: BaseNavigator by navigatorViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(false)
        }
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogIncorrectBinding,
    ) {
        lifecycleScope.launch {
            delay(1000)
            dismiss()
        }
    }
}
