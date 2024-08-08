package com.english.vocab.di

import com.english.vocab.MainNavigator
import com.english.vocab.ui.tutorial.TutorialNavigator
import com.koai.base.main.extension.navigatorViewModel
import org.koin.dsl.module

object NavigatorModule {
    fun init() =
        module {
            navigatorViewModel { MainNavigator() }
            navigatorViewModel { TutorialNavigator() }
        }
}
