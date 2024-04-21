package com.koai.kingofenglish.di

import com.koai.kingofenglish.domain.usecase.AddUserUseCase
import com.koai.kingofenglish.domain.usecase.AddUserUseCaseImpl
import com.koai.kingofenglish.domain.usecase.BackgroundUseCase
import com.koai.kingofenglish.domain.usecase.BackgroundUseCaseImpl
import com.koai.kingofenglish.domain.usecase.GetQuestionUseCase
import com.koai.kingofenglish.domain.usecase.GetQuestionUseCaseImpl
import com.koai.kingofenglish.domain.usecase.GetTopRankUseCase
import com.koai.kingofenglish.domain.usecase.GetTopRankUseCaseImpl
import com.koai.kingofenglish.domain.usecase.GetUserUseCase
import com.koai.kingofenglish.domain.usecase.GetUserUseCaseImpl
import com.koai.kingofenglish.domain.usecase.UpdateUserUseCase
import com.koai.kingofenglish.domain.usecase.UpdateUserUseCaseImpl
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
