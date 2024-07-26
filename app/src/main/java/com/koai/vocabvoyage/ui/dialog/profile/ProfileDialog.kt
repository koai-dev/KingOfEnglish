package com.koai.vocabvoyage.ui.dialog.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.base.utils.SharePreference
import com.koai.vocabvoyage.MainNavigator
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.DialogProfileBinding
import com.koai.vocabvoyage.domain.account.AccountUtils
import com.koai.vocabvoyage.utils.AppConfig
import com.koai.vocabvoyage.utils.Constants
import org.koin.android.ext.android.inject

class ProfileDialog :
    BaseDialog<DialogProfileBinding, ProfileRouter, MainNavigator>(R.layout.dialog_profile) {
    override val navigator: MainNavigator by navigatorViewModel()
    private val sharePreference: SharePreference by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(true)
        }
    }

    override fun onStart() {
        super.onStart()
        this@ProfileDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogProfileBinding,
    ) {
        binding.btnClose.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
        }

        binding.btnSignOut.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            if (binding.btnSignOut.text != "Login") {
                AccountUtils.signOut()
                sharePreference.setIntPref(Constants.CURRENT_QUESTION, 1)
                sharePreference.setIntPref(Constants.CURRENT_POINTS, 0)
            }
            dismiss()
            router?.gotoLoginScreen()
        }
    }
}
