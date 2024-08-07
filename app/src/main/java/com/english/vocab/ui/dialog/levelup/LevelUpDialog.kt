package com.english.vocab.ui.dialog.levelup

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
import com.english.vocab.databinding.DialogLevelUpBinding
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.Constants
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog

class LevelUpDialog :
    BaseDialog<DialogLevelUpBinding, BaseRouter, MainNavigator>(R.layout.dialog_level_up) {
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
        this@LevelUpDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogLevelUpBinding,
    ) {
        var pointAdd = arguments?.getInt(Constants.ADDED_POINTS) ?: 0
        val img = arguments?.getString(Constants.IMG)
        if (pointAdd == 0) {
            pointAdd = -600
        }
        binding.img = img
        binding.pointAdded = pointAdd
        binding.btnHome.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd))
            router?.onPopScreen()
        }

        binding.btnNormal.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd))
        }

        binding.btnDoublePoint.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            if (pointAdd <= 150) {
                pointAdd = 300
            } else {
                pointAdd *= 2
            }
            setFragmentResult(Constants.WATCH_ADS, bundleOf(Constants.ADDED_POINTS to pointAdd))
        }

        binding.btnShare.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            setFragmentResult(Constants.SHARE, bundleOf(Constants.ADDED_POINTS to pointAdd))
        }

        binding.btnAds.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            if (pointAdd <= 150) {
                pointAdd = 300
            } else {
                pointAdd *= 2
            }
            setFragmentResult(Constants.WATCH_ADS, bundleOf(Constants.ADDED_POINTS to pointAdd))
        }
    }
}
