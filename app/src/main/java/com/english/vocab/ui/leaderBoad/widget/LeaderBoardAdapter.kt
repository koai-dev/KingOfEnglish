package com.english.vocab.ui.leaderBoad.widget

import com.english.vocab.R
import com.english.vocab.databinding.ItemLeaderBoardBinding
import com.english.vocab.domain.models.User
import com.english.vocab.utils.convertNumber
import com.koai.base.main.adapter.BaseListAdapter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale

class LeaderBoardAdapter : BaseListAdapter<User>() {
    override fun getLayoutId(): Int = R.layout.item_leader_board

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
    ) {
        (holder.binding as ItemLeaderBoardBinding).apply {
            point = getItem(holder.absoluteAdapterPosition).points.convertNumber()
            user =
                getItem(holder.absoluteAdapterPosition).apply {
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
