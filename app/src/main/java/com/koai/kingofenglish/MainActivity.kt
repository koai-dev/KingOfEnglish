package com.koai.kingofenglish

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.BaseActivity
import com.koai.base.main.action.router.BaseRouter
import com.koai.kingofenglish.databinding.ActivityMainBinding

class MainActivity :
    BaseActivity<ActivityMainBinding, BaseRouter, MainNavigator>(R.layout.activity_main) {
    override fun getModelNavigator() = ViewModelProvider(this)[MainNavigator::class.java]

    override fun initView(savedInstanceState: Bundle?, binding: ActivityMainBinding) {

    }

}