package com.koai.kingofenglish.ui.theme

import com.koai.base.main.action.router.BaseRouter

interface CustomThemeRouter: BaseRouter {
    fun setBackground(url: String)
}