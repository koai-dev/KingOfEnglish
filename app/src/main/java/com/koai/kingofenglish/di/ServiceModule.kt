package com.koai.kingofenglish.di

import com.koai.kingofenglish.ads.AdsRepository
import com.koai.kingofenglish.ads.AdsRepositoryImpl
import com.koai.kingofenglish.service.ApiClient
import com.koai.kingofenglish.service.ApiService
import org.koin.dsl.module

object ServiceModule {
    fun init() =
        module {
            factory<ApiService?> { ApiClient.getService(get()) }
            includes(UseCaseModule.init())
            factory<AdsRepository> { AdsRepositoryImpl(get()) }
        }
}
