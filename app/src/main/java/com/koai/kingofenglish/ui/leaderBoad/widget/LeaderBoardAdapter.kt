package com.koai.kingofenglish.ui.leaderBoad.widget

import com.koai.base.main.adapter.BaseListAdapter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ItemLeaderBoardBinding
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.utils.convertNumber

class LeaderBoardAdapter : BaseListAdapter<User>() {
    override fun getLayoutId(): Int = R.layout.item_leader_board

    override fun onBindViewHolder(holder: VH, position: Int) {
        (holder.binding as ItemLeaderBoardBinding).apply {
            point = getItem(holder.bindingAdapterPosition).points.convertNumber()
            user = getItem(holder.bindingAdapterPosition).apply {
                this.name = this.name?.split(" ")?.last()
                this.avatar = if (this.avatar.isNullOrEmpty()) null else this.avatar
            }
            pos = holder.bindingAdapterPosition + 1
            root.setClickableWithScale {

            }
            executePendingBindings()
        }
    }
}