package com.koai.vocabvoyage

import com.google.android.gms.ads.MobileAds
import com.koai.base.BaseApplication
import com.koai.vocabvoyage.di.NavigatorModule
import com.koai.vocabvoyage.di.ServiceModule
import com.koai.vocabvoyage.di.ViewModelModule
import com.koai.vocabvoyage.utils.AppConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module

class MyApplication : BaseApplication() {
    override fun appModule() = module {
        includes(
            super.appModule(),
            ServiceModule.init(),
            NavigatorModule.init(),
            ViewModelModule.init(),
        )
    }

    override fun onCreate() {
        super.onCreate()
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MyApplication) {}
            AppConfig.initRemote()
        }
    }
}
