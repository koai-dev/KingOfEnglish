package com.koai.kingofenglish

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.BaseActivity
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.kingofenglish.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<ActivityMainBinding, BaseRouter, MainNavigator>(R.layout.activity_main) {
    override val navigator: MainNavigator by viewModel()
    override fun initView(savedInstanceState: Bundle?, binding: ActivityMainBinding) {

    }
}
