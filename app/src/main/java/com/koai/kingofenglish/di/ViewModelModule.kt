package com.koai.kingofenglish.di

import com.koai.kingofenglish.ui.play.PlayViewModel
import org.koin.dsl.module

object ViewModelModule {
    fun init() = module {
        factory {  PlayViewModel(get()) }
    }
}