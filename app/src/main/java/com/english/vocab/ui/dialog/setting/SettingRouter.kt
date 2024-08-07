package com.english.vocab.ui.dialog.setting

import com.koai.base.main.action.router.BaseRouter

interface SettingRouter : BaseRouter {
    fun turnOnMusic()

    fun turnOffMusic()

    fun turnOnSoundEffect()

    fun turnOffSoundEffect()

    fun turnOnVibrate()

    fun turnOffVibrate()

    fun gotoTerm()

    fun gotoReport()
}
