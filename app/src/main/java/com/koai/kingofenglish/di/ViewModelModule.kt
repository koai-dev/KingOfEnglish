package com.koai.kingofenglish.di

import com.koai.base.main.extension.journeyViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.kingofenglish.MainViewModel
import com.koai.kingofenglish.ui.play.PlayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import javax.inject.Qualifier

object ViewModelModule {
    fun init() = module {
        viewModel { MainViewModel()  }
        screenViewModel {PlayViewModel(get(), get())  }
    }
}