package com.koai.kingofenglish.ui.dialog

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseDialog
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogLevelUpBinding
import com.koai.kingofenglish.network.QuestionViewModel
import com.koai.kingofenglish.ui.play.PlayRouter
import kotlinx.coroutines.launch


class DialogLevelUp : BaseDialog<DialogLevelUpBinding, PlayRouter, MainNavigator>(R.layout.dialog_level_up) {
    private val viewModel : QuestionViewModel by viewModels()
    private var textAnswer : String = ""
    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]

    override fun initView(savedInstanceState: Bundle?, binding: DialogLevelUpBinding) {

        setupsView()
        actionViews()
        getData()
    }

    private fun getData() {
        viewModel.getQuestionByLevel(SharePreference.getIntPref(requireContext(),"LEVEL"),requireContext())
        lifecycleScope.launch {
            viewModel.questionStateFlow.collect {
                if (it != null) {
                    textAnswer = it.data?.answerRight.toString()
                }
            }
        }
    }

    private fun actionViews() {
        binding.ctnBtnNext.setClickableWithScale {
            val bundle = Bundle().apply {
                putString("QUESTION", textAnswer)
            }
            router?.reloadPlay(bundle)
        }
        binding.ctnBtnHome.setClickableWithScale {
            router?.backToHome()
        }
    }

    private fun setupsView() {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val layoutParams = WindowManager.LayoutParams()
        val window = dialog?.window
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
    }

}
