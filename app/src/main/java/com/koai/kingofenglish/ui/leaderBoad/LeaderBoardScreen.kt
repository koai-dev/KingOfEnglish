package com.koai.kingofenglish.ui.leaderBoad

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.koai.base.main.action.event.ShareFile
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.LayoutShareMySelfBinding
import com.koai.kingofenglish.databinding.ScreenLeaderBoardBinding
import com.koai.kingofenglish.domain.account.AccountUtils
import com.koai.kingofenglish.ui.leaderBoad.widget.LeaderBoardAdapter
import com.koai.kingofenglish.utils.AppConfig
import com.koai.kingofenglish.utils.convertNumber
import com.koai.kingofenglish.utils.saveBitmapToCache
import com.koai.kingofenglish.utils.toBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LeaderBoardScreen :
    BaseScreen<ScreenLeaderBoardBinding, LeaderBoardRouter, MainNavigator>(R.layout.screen_leader_board) {
    override val navigator: MainNavigator by navigatorViewModel()
    private lateinit var adapter: LeaderBoardAdapter
    private val viewmodel: LeaderBoardViewModel by screenViewModel()

    override fun initView(savedInstanceState: Bundle?, binding: ScreenLeaderBoardBinding) {
        binding.img = AppConfig.background
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
            val shareBinding = LayoutShareMySelfBinding.inflate(layoutInflater)
            shareBinding.user = AccountUtils.user
            CoroutineScope(Dispatchers.IO).launch {
                val uri = shareBinding.root.toBitmap().saveBitmapToCache(activity)
                if (uri != null) {
                    router?.onShareFile(
                        bundleOf(
                            ShareFile.TITLE to "Share this to Best-friends",
                            ShareFile.EXTRA to uri.toString()
                        )
                    )
                }
            }

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
//                binding.layoutLeaderBoardHeader.top3 = it.data.data?.get(2)?.apply {
//                    this.name = this.name?.split(" ")?.last()
//                    this.avatar = if (this.avatar.isNullOrEmpty()) null else this.avatar
//                }
            }
        }
    }

}