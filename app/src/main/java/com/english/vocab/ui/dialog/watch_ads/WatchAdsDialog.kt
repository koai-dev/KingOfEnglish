package com.english.vocab.ui.dialog.watch_ads

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
import com.english.vocab.ads.AdmobUtils
import com.english.vocab.ads.AdsViewModel
import com.english.vocab.databinding.DialogWatchAdsBinding
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.Constants
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.journeyViewModel
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog

class WatchAdsDialog :
    BaseDialog<DialogWatchAdsBinding, BaseRouter, MainNavigator>(R.layout.dialog_watch_ads) {
    override val navigator: MainNavigator by navigatorViewModel()
    private val adsViewModel: AdsViewModel by journeyViewModel()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(false)
        }
    }

    override fun onStart() {
        super.onStart()
        this@WatchAdsDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    override fun initView(
        savedInstanceState: Bundle?,
        binding: DialogWatchAdsBinding,
    ) {
        binding.btnWatchAds.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            adsViewModel.showAdsOneTime(
                activity,
                object : AdmobUtils.Action {
                    override fun onReward() {
                        dismiss()
                        setFragmentResult(Constants.SHOW_TIP, bundleOf())
                    }
                },
            )
        }

        binding.btnClose.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            dismiss()
            setFragmentResult(Constants.RESUME, bundleOf())
        }
    }
}
