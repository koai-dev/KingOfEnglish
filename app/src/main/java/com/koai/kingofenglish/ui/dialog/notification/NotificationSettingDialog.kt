package com.koai.kingofenglish.ui.dialog.notification

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogNotificationSettingBinding
import com.koai.kingofenglish.utils.AppConfig
import com.koai.kingofenglish.utils.Constants
import org.koin.android.ext.android.inject

class NotificationSettingDialog :
    BaseDialog<DialogNotificationSettingBinding, NotificationSettingRouter, MainNavigator>(
        R.layout.dialog_notification_setting
    ) {
    private val sharePreference by inject<SharePreference>()

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
        this@NotificationSettingDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
    }

    override fun initView(savedInstanceState: Bundle?, binding: DialogNotificationSettingBinding) {
        binding.btnClose.setClickableWithScale(AppConfig.enableSoundEffect) {
            dismiss()
            AppConfig.showPopupNotificationSetting = true
            val isFirstLaunch = !sharePreference.getBooleanPref(Constants.IS_FIRST_LAUNCH)
            if (isFirstLaunch) {
                router?.gotoTutorial()
                sharePreference.setBooleanPref(Constants.IS_FIRST_LAUNCH, true)
            }
        }
        binding.btnAgree.setClickableWithScale(AppConfig.enableSoundEffect) {
            dismiss()
            AppConfig.showPopupNotificationSetting = true
            router?.gotoNotificationSetting(activity)
        }
    }
}