package com.koai.kingofenglish.ui.login

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenLoginBinding

class LoginScreen :
    BaseScreen<ScreenLoginBinding, LoginRouter, MainNavigator>(R.layout.screen_login) {
    override fun initView(savedInstanceState: Bundle?, binding: ScreenLoginBinding) {
        actionView()
        setupView()
    }

    private fun setupView() {
        val spannableStringBuilder =
            SpannableStringBuilder(binding.ctnLinkAccount.textView.text.toString())
        val fcsTerms = ForegroundColorSpan(resources.getColor(R.color.black_tran))
        val fcsPrivacyPolicy = ForegroundColorSpan(resources.getColor(R.color.black_tran))
        spannableStringBuilder.setSpan(fcsTerms, 29, 34, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.setSpan(fcsPrivacyPolicy, 37, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.ctnLinkAccount.textView.text = spannableStringBuilder
    }

    private fun actionView(){
        binding.ctnLinkAccount.btnNo.setClickableWithScale {
            router?.goToHome()
        }

        binding.ctnLinkAccount.btnYes.setClickableWithScale {
        }
    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}
