package com.koai.kingofenglish.ui.play

import com.koai.base.main.action.router.BaseRouter

interface PlayRouter : BaseRouter {
    fun onPause()

    fun gotoTip(img: String)

    fun nextLevel(currentPoint: Int)

    fun showWrongToast()

    fun watchAds()
}