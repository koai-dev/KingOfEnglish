package com.koai.kingofenglish.ui.dialog.levelup

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.koai.base.main.action.navigator.BaseNavigator
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogLevelUpBinding
import com.koai.kingofenglish.utils.Constants

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
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun initView(savedInstanceState: Bundle?, binding: DialogLevelUpBinding) {
        var pointAdd = arguments?.getInt(Constants.ADDED_POINTS) ?: 0
        if (pointAdd==0){
            pointAdd = -600
        }
        binding.pointAdded = pointAdd
        binding.btnHome.setClickableWithScale {
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd))
            navigator.offNavScreen(R.id.action_global_homeScreen)
            dismiss()
        }

        binding.btnNormal.setClickableWithScale {
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd))
            dismiss()
        }

        binding.btnDoublePoint.setClickableWithScale {
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd * 2))
            dismiss()
        }

        binding.btnShare.setClickableWithScale {
//            router?.onShareFile()
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd))
            dismiss()
        }

        binding.btnAds.setClickableWithScale {
            setFragmentResult(Constants.ADDED_POINTS, bundleOf(Constants.ADDED_POINTS to pointAdd * 2))
            dismiss()
        }
    }
}