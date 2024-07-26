package com.koai.vocabvoyage.di

import com.koai.vocabvoyage.ads.AdsRepository
import com.koai.vocabvoyage.ads.AdsRepositoryImpl
import com.koai.vocabvoyage.service.ApiClient
import com.koai.vocabvoyage.service.ApiService
import org.koin.dsl.module

object ServiceModule {
    fun init() =
        module {
            factory<ApiService?> { ApiClient.getService(get()) }
            includes(UseCaseModule.init())
            factory<AdsRepository> { AdsRepositoryImpl(get()) }
        }
}
