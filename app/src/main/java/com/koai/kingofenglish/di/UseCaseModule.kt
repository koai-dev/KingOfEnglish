package com.koai.kingofenglish.di

import com.koai.kingofenglish.domain.usecase.GetQuestionUseCase
import com.koai.kingofenglish.domain.usecase.GetQuestionUseCaseImpl
import org.koin.dsl.module

object UseCaseModule {
    fun init() = module {
        factory<GetQuestionUseCase> { GetQuestionUseCaseImpl(get()) }
    }
}