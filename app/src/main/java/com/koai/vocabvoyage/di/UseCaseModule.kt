package com.koai.vocabvoyage.di

import com.koai.vocabvoyage.domain.usecase.AddUserUseCase
import com.koai.vocabvoyage.domain.usecase.AddUserUseCaseImpl
import com.koai.vocabvoyage.domain.usecase.BackgroundUseCase
import com.koai.vocabvoyage.domain.usecase.BackgroundUseCaseImpl
import com.koai.vocabvoyage.domain.usecase.GetQuestionUseCase
import com.koai.vocabvoyage.domain.usecase.GetQuestionUseCaseImpl
import com.koai.vocabvoyage.domain.usecase.GetTopRankUseCase
import com.koai.vocabvoyage.domain.usecase.GetTopRankUseCaseImpl
import com.koai.vocabvoyage.domain.usecase.GetUserUseCase
import com.koai.vocabvoyage.domain.usecase.GetUserUseCaseImpl
import com.koai.vocabvoyage.domain.usecase.UpdateUserUseCase
import com.koai.vocabvoyage.domain.usecase.UpdateUserUseCaseImpl
import org.koin.dsl.module

object UseCaseModule {
    fun init() =
        module {
            factory<GetQuestionUseCase> { GetQuestionUseCaseImpl(get()) }
            factory<GetUserUseCase> { GetUserUseCaseImpl(get()) }
            factory<GetTopRankUseCase> { GetTopRankUseCaseImpl(get()) }
            factory<UpdateUserUseCase> { UpdateUserUseCaseImpl(get()) }
            factory<AddUserUseCase> { AddUserUseCaseImpl(get()) }
            factory<BackgroundUseCase> { BackgroundUseCaseImpl(get()) }
        }
}
