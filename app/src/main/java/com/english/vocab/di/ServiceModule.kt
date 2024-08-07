package com.english.vocab.di

import com.english.vocab.ads.AdsRepository
import com.english.vocab.ads.AdsRepositoryImpl
import com.english.vocab.service.ApiClient
import com.english.vocab.service.ApiService
import org.koin.dsl.module

object ServiceModule {
    fun init() =
        module {
            factory<ApiService?> { ApiClient.getService(get()) }
            includes(UseCaseModule.init())
            factory<AdsRepository> { AdsRepositoryImpl(get()) }
        }
}
