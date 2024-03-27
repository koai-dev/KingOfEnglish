package com.koai.kingofenglish.ui.home

import com.koai.base.main.action.router.BaseRouter

interface HomeRouter : BaseRouter {
    fun gotoPlay()
    fun gotoCustomTheme()
    fun gotoSetting()
    fun gotoTutorial()
}