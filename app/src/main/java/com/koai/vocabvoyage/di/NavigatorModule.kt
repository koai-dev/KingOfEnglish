package com.koai.vocabvoyage.di

import com.koai.base.main.extension.navigatorViewModel
import com.koai.vocabvoyage.MainNavigator
import com.koai.vocabvoyage.ui.tutorial.TutorialNavigator
import org.koin.dsl.module

object NavigatorModule {
    fun init() =
        module {
            navigatorViewModel { MainNavigator() }
            navigatorViewModel { TutorialNavigator() }
        }
}
