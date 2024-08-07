package com.english.vocab.di

import com.english.vocab.domain.usecase.AddUserUseCase
import com.english.vocab.domain.usecase.AddUserUseCaseImpl
import com.english.vocab.domain.usecase.BackgroundUseCase
import com.english.vocab.domain.usecase.BackgroundUseCaseImpl
import com.english.vocab.domain.usecase.GetQuestionUseCase
import com.english.vocab.domain.usecase.GetQuestionUseCaseImpl
import com.english.vocab.domain.usecase.GetTopRankUseCase
import com.english.vocab.domain.usecase.GetTopRankUseCaseImpl
import com.english.vocab.domain.usecase.GetUserUseCase
import com.english.vocab.domain.usecase.GetUserUseCaseImpl
import com.english.vocab.domain.usecase.UpdateUserUseCase
import com.english.vocab.domain.usecase.UpdateUserUseCaseImpl
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
