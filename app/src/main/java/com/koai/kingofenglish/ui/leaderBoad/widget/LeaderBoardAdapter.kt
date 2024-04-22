package com.koai.kingofenglish.ui.leaderBoad.widget

import com.koai.base.main.adapter.BaseListAdapter
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ItemLeaderBoardBinding
import com.koai.kingofenglish.domain.models.User

class LeaderBoardAdapter : BaseListAdapter<User>() {
    override fun getLayoutId(): Int = R.layout.item_leader_board

    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder.binding as ItemLeaderBoardBinding).apply {

            executePendingBindings()
        }
    }
}