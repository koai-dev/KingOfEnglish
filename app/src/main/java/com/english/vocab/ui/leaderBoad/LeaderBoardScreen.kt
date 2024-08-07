package com.english.vocab.ui.leaderBoad

import android.os.Bundle
import com.english.vocab.R
import com.english.vocab.databinding.ScreenLeaderBoardBinding
import com.english.vocab.domain.account.AccountUtils
import com.english.vocab.ui.leaderBoad.widget.LeaderBoardAdapter
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus
import com.english.vocab.MainNavigator
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.convertNumber
import com.english.vocab.utils.share

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
        setAction()
    }

    private fun setAction() {
        binding.layoutLeaderBoardHeader.btnShare.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            binding.shareView.share(activity, router)
        }
    }

    private fun getData() {
        viewmodel.getTopLeaders()
    }

    private fun observer() {
        viewmodel.leaders.observe(this) {
            if (it is ResponseStatus.Success) {
                adapter.submitList(it.data.data)
                binding.layoutLeaderBoardHeader.top1 = it.data.data?.get(0).apply {
                    this?.name = this?.name?.split(" ")?.last()
                    this?.avatar = if (this?.avatar.isNullOrEmpty()) null else this?.avatar
                }
                binding.layoutLeaderBoardHeader.top2 = it.data.data?.get(1)?.apply {
                    this.name = this.name?.split(" ")?.last()
                    this.avatar = if (this.avatar.isNullOrEmpty()) null else this.avatar
                }
                binding.layoutLeaderBoardHeader.top3 = it.data.data?.get(2)?.apply {
                    this.name = this.name?.split(" ")?.last()
                    this.avatar = if (this.avatar.isNullOrEmpty()) null else this.avatar
                }
            }
        }
    }

}