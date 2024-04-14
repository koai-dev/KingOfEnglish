package com.koai.kingofenglish.ui.dialog.watch_ads

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogWatchAdsBinding
import com.koai.kingofenglish.utils.AdmobUtils
import com.koai.kingofenglish.utils.Constants

class WatchAdsDialog :
    BaseDialog<DialogWatchAdsBinding, BaseRouter, MainNavigator>(R.layout.dialog_watch_ads) {
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
        this@WatchAdsDialog.dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun initView(savedInstanceState: Bundle?, binding: DialogWatchAdsBinding) {
        binding.btnWatchAds.setClickableWithScale {
            AdmobUtils.showAdmob(activity, object : AdmobUtils.Action{
                override fun onReward() {
                    setFragmentResult(Constants.SHOW_TIP, bundleOf())
                    dismiss()
                }
            })
        }
    }
}