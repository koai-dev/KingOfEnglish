package com.koai.kingofenglish.di

import com.koai.base.main.extension.screenViewModel
import com.koai.kingofenglish.MainViewModel
import com.koai.kingofenglish.ads.AdsViewModel
import com.koai.kingofenglish.ui.play.PlayViewModel
import com.koai.kingofenglish.ads.AdsWorkManager
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    fun init() = module {
        viewModel { MainViewModel() }
        screenViewModel { PlayViewModel(get(), get()) }
        viewModel { AdsViewModel(get()) }
    }
}