package com.koai.kingofenglish.ui.play

import com.koai.base.main.action.router.BaseRouter

interface PlayRouter : BaseRouter {
    fun onPause()

    fun gotoHome()
    fun gotoTip()
}