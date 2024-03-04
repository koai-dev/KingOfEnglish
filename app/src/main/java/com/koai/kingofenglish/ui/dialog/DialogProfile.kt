package com.koai.kingofenglish.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.closeKeyBoard
import com.koai.base.main.screens.BaseDialog
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.DialogProfileBinding

class DialogProfile :
    BaseDialog<DialogProfileBinding, BaseRouter, MainNavigator>(R.layout.dialog_profile) {


    override fun initView(savedInstanceState: Bundle?, binding: DialogProfileBinding) {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val savedText = SharePreference.getStringPref(requireContext(), "NAME")
        binding.edtUsername.setText(savedText)
        actionViews()
    }

    private fun actionViews() {
        binding.btnClose.setClickableWithScale{
            dismiss()
        }
        binding.edtUsername.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = binding.edtUsername.text.toString()
                SharePreference.setStringPref(requireContext(), "NAME", text)
                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val currentFocus = requireActivity().currentFocus
                currentFocus?.let {
                    inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)}
                true // Trả về true để chỉ định rằng bạn đã xử lý sự kiện này
            } else {
                false // Trả về false để cho Android biết rằng bạn không xử lý sự kiện này
            }
        }

    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}