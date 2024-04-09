package com.koai.kingofenglish

import com.koai.base.BaseApplication
import com.koai.kingofenglish.di.NavigatorModule
import com.koai.kingofenglish.di.ServiceModule
import com.koai.kingofenglish.di.ViewModelModule
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
}