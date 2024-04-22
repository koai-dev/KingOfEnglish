package com.koai.kingofenglish.ui.leaderBoad

import android.os.Bundle
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenLeaderBoardBinding
import com.koai.kingofenglish.ui.leaderBoad.widget.LeaderBoardAdapter

class LeaderBoardScreen :
    BaseScreen<ScreenLeaderBoardBinding, LeaderBoardRouter, MainNavigator>(R.layout.screen_leader_board) {
    override val navigator: MainNavigator by navigatorViewModel()
    private lateinit var adapter: LeaderBoardAdapter
    private val viewmodel: LeaderBoardViewModel by screenViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenLeaderBoardBinding) {
        adapter = LeaderBoardAdapter()

        observer()
        getData()
    }

    private fun getData(){
        viewmodel.getTopLeaders()
    }

    private fun observer(){
        viewmodel.leaders.observe(this){
            if(it is ResponseStatus.Success){
                adapter.submitList(it.data.data)
            }
        }
    }

}