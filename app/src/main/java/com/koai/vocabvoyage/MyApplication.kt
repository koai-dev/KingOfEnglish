package com.koai.vocabvoyage

import com.koai.base.BaseApplication
import com.koai.vocabvoyage.di.NavigatorModule
import com.koai.vocabvoyage.di.ServiceModule
import com.koai.vocabvoyage.di.ViewModelModule
import com.koai.vocabvoyage.utils.AppConfig
import org.koin.dsl.module

class MyApplication : BaseApplication() {
    override fun appModule() =
        module {
            includes(
                super.appModule(),
                ServiceModule.init(),
                NavigatorModule.init(),
                ViewModelModule.init(),
            )
        }

    override fun onCreate() {
        super.onCreate()
        AppConfig.initRemote()
    }
}
