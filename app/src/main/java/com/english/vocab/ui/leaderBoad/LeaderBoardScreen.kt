package com.english.vocab.ui.leaderBoad

import android.os.Bundle
import com.english.vocab.MainActivity
import com.english.vocab.MainNavigator
import com.english.vocab.R
import com.english.vocab.databinding.ScreenLeaderBoardBinding
import com.english.vocab.domain.account.AccountUtils
import com.english.vocab.ui.leaderBoad.widget.LeaderBoardAdapter
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.convertNumber
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus

class LeaderBoardScreen :
    BaseScreen<ScreenLeaderBoardBinding, LeaderBoardRouter, MainNavigator>(R.layout.screen_leader_board) {
    override val navigator: MainNavigator by navigatorViewModel()
    private lateinit var adapter: LeaderBoardAdapter
    private val viewmodel: LeaderBoardViewModel by screenViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenLeaderBoardBinding) {
        if (AppConfig.background.isNullOrEmpty()) {
            binding.imgBg.loadImage(R.drawable.bg_home)
        } else {
            binding.img = AppConfig.background
        }
        binding.layoutLeaderBoardFooter.apply {
            user = AccountUtils.user
            point = AccountUtils.user?.points.convertNumber()
        }
        adapter = LeaderBoardAdapter()
        binding.rcv.adapter = adapter
        observer()
        getData()
    }

    private fun getData() {
        viewmodel.getTopLeaders()
    }

    private fun observer() {
        viewmodel.leaders.observe(this) {
            if (it is ResponseStatus.Success) {
                adapter.submitList(it.data.data)
            }
        }
    }

}