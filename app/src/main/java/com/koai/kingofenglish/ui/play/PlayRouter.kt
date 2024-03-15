package com.koai.kingofenglish.ui.play

import android.os.Bundle
import com.koai.base.main.action.router.BaseRouter

interface PlayRouter : BaseRouter {
    fun dialogLevelUp()
    fun goToHome()
    fun reloadPlay(bundle: Bundle)
    fun dialogPause()
    fun goToLeaderBoard()
}