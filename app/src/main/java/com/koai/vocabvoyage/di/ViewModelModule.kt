package com.koai.vocabvoyage.di

import com.koai.base.main.extension.screenViewModel
import com.koai.vocabvoyage.MainViewModel
import com.koai.vocabvoyage.ads.AdsViewModel
import com.koai.vocabvoyage.ui.leaderBoad.LeaderBoardViewModel
import com.koai.vocabvoyage.ui.login.LoginViewModel
import com.koai.vocabvoyage.ui.play.PlayViewModel
import com.koai.vocabvoyage.ui.theme.CustomThemeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    fun init() = module {
        viewModel { MainViewModel() }
        screenViewModel { PlayViewModel(get(), get(), get()) }
        viewModel { AdsViewModel(get()) }
        screenViewModel { LoginViewModel(get(), get(), get()) }
        screenViewModel { CustomThemeViewModel(get(), get()) }
        screenViewModel { LeaderBoardViewModel(get()) }
    }
}
