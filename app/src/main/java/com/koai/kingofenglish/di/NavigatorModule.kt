package com.koai.kingofenglish.di

import com.koai.base.main.extension.navigatorViewModel
import com.koai.kingofenglish.MainNavigator
import org.koin.dsl.module

object NavigatorModule {
    fun init() = module {
        navigatorViewModel { MainNavigator() }
    }
}