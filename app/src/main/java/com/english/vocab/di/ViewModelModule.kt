package com.english.vocab.di

import com.english.vocab.MainViewModel
import com.english.vocab.ads.AdsViewModel
import com.english.vocab.ui.leaderBoad.LeaderBoardViewModel
import com.english.vocab.ui.login.LoginViewModel
import com.english.vocab.ui.play.PlayViewModel
import com.english.vocab.ui.theme.CustomThemeViewModel
import com.koai.base.main.extension.screenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    fun init() =
        module {
            viewModel { MainViewModel(get()) }
            screenViewModel { PlayViewModel(get(), get(), get()) }
            viewModel { AdsViewModel(get()) }
            screenViewModel { LoginViewModel(get(), get(), get()) }
            screenViewModel { CustomThemeViewModel(get(), get()) }
            screenViewModel { LeaderBoardViewModel(get()) }
        }
}
