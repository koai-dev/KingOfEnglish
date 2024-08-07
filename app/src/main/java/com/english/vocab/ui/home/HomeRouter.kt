package com.english.vocab.ui.home

import com.koai.base.main.action.router.BaseRouter

interface HomeRouter : BaseRouter {
    fun gotoPlayScreen()

    fun gotoCustomThemeScreen()

    fun gotoSettingScreen()

    fun gotoTutorial()

    fun gotoLeaderBoardScreen()

    fun gotoProfile()

    fun gotoNotificationSetting()

    fun gotoUpdate()
}
