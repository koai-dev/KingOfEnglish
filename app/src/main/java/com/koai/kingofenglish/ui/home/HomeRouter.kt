package com.koai.kingofenglish.ui.home

import android.os.Bundle
import com.koai.base.main.action.router.BaseRouter

interface HomeRouter : BaseRouter {
    fun goToPlay(bundle: Bundle)
    fun dialogProfile()
    fun dialogSetting()
}