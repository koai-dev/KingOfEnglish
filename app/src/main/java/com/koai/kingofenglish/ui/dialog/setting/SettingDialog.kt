package com.koai.kingofenglish.ui.dialog.setting

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
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogSettingBinding
import com.koai.kingofenglish.utils.Constants
import org.koin.android.ext.android.inject

class SettingDialog :
    BaseDialog<DialogSettingBinding, SettingRouter, MainNavigator>(R.layout.dialog_setting) {
    override val navigator: MainNavigator by navigatorViewModel()
    private val sharePreference : SharePreference by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(true)
        }
    }

    override fun onStart() {
        super.onStart()
        this@SettingDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun initView(savedInstanceState: Bundle?, binding: DialogSettingBinding) {
        binding.enableMusic = !sharePreference.getBooleanPref(Constants.DISABLE_MUSIC)
        binding.enableVibrate = !sharePreference.getBooleanPref(Constants.DISABLE_VIBRATE)
        binding.enableSoundEffect = !sharePreference.getBooleanPref(Constants.DISABLE_SOUND_EFFECT)
        binding.btnMusic.setClickableWithScale {
            binding.enableMusic = binding.enableMusic != true
            if (binding.enableMusic == true) {
                router?.turnOnMusic()
                sharePreference.setBooleanPref(Constants.DISABLE_MUSIC, false)
            } else {
                router?.turnOffMusic()
                sharePreference.setBooleanPref(Constants.DISABLE_MUSIC, true)
            }
        }
        binding.btnVibrate.setClickableWithScale {
            binding.enableVibrate = binding.enableVibrate != true
            if (binding.enableVibrate == true) {
                router?.turnOnVibrate()
                sharePreference.setBooleanPref(Constants.DISABLE_VIBRATE, false)
            } else {
                router?.turnOffVibrate()
                sharePreference.setBooleanPref(Constants.DISABLE_VIBRATE, true)
            }
        }
        binding.btnSoundEffect.setClickableWithScale {
            binding.enableSoundEffect = binding.enableSoundEffect != true
            if (binding.enableSoundEffect == true) {
                router?.turnOnSoundEffect()
                sharePreference.setBooleanPref(Constants.DISABLE_SOUND_EFFECT, false)
            } else {
                router?.turnOffSoundEffect()
                sharePreference.setBooleanPref(Constants.DISABLE_SOUND_EFFECT, true)
            }
        }
        binding.btnReport.setClickableWithScale {
            dismiss()
            router?.gotoReport()
        }
        binding.txtTerm.setClickableWithScale {
            dismiss()
            router?.gotoTerm()
        }
        binding.btnClose.setClickableWithScale {
            dismiss()
        }
    }
}