package com.english.vocab

import com.blongho.country_data.World
import com.english.vocab.di.NavigatorModule
import com.english.vocab.di.ServiceModule
import com.english.vocab.di.ViewModelModule
import com.english.vocab.utils.AppConfig
import com.google.android.gms.ads.MobileAds
import com.koai.base.BaseApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            World.init(this@MyApplication)
        }
    }
}
