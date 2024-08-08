package com.english.vocab.ui.play

import com.koai.base.main.action.router.BaseRouter

interface PlayRouter : BaseRouter {
    fun onPause()

    fun gotoTip(img: String)

    fun nextLevel(
        currentPoint: Int,
        img: String?,
    )

    fun showWrongToast()

    fun watchAds()

    fun lose()
}
