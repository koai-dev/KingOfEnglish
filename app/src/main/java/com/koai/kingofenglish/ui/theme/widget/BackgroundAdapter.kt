package com.koai.kingofenglish.ui.theme.widget

import com.koai.base.main.adapter.BaseListAdapter
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ItemBackgroundBinding
import com.koai.kingofenglish.domain.account.AccountUtils
import com.koai.kingofenglish.domain.models.Background

class BackgroundAdapter : BaseListAdapter<Background>() {
    override fun getLayoutId(): Int = R.layout.item_background
    override fun onBindViewHolder(holder: VH, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder.binding as ItemBackgroundBinding).apply {
            img = getItem(holder.bindingAdapterPosition).link
            levelBackground = getItem(holder.bindingAdapterPosition).id * 15
            hasLocked =
                getItem(holder.bindingAdapterPosition).id * 15 > (AccountUtils.user?.currentLevel
                    ?: 0)
            listener?.click(holder.bindingAdapterPosition, getItem(holder.bindingAdapterPosition), code = if (getItem(holder.bindingAdapterPosition).id * 15 > (AccountUtils.user?.currentLevel
                    ?: 0)) 0 else 1)
            executePendingBindings()
        }
    }
}