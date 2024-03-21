package com.koai.kingofenglish.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenLoginBinding

class LoginScreen :
    BaseScreen<ScreenLoginBinding, LoginRouter, MainNavigator>(R.layout.screen_login) {
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenLoginBinding,
    ) {
        actionView()
    }

    private fun actionView() {
        binding.ctnLinkAccount.btnNo.setClickableWithScale {
            // todo
        }

        binding.ctnLinkAccount.btnYes.setClickableWithScale {
            // todo
        }
    }

    override val navigator: MainNavigator by navigatorViewModel()
}
