package com.koai.kingofenglish.ui.home

import com.koai.base.main.action.router.BaseRouter

interface HomeRouter : BaseRouter {
    fun gotoPlayScreen()
    fun gotoCustomThemeScreen()
    fun gotoSettingScreen()
    fun gotoTutorial()
    fun gotoLeaderBoardScreen()
    fun gotoProfile()
}