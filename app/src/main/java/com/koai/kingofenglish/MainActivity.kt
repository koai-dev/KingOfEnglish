package com.koai.kingofenglish

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.ads.MobileAds
import com.koai.base.main.BaseActivity
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions
import com.koai.base.main.extension.invisible
import com.koai.base.main.extension.visible
import com.koai.base.network.ResponseStatus
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.ads.AdsViewModel
import com.koai.kingofenglish.databinding.ActivityMainBinding
import com.koai.kingofenglish.service.Socket
import com.koai.kingofenglish.ui.leaderBoad.LeaderBoardViewModel
import com.koai.kingofenglish.utils.AppConfig
import com.koai.kingofenglish.utils.Constants
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<ActivityMainBinding, BaseRouter, MainNavigator>(R.layout.activity_main) {
    override val navigator: MainNavigator by viewModel()
    private var mediaPlayer: MediaPlayer? = null
    private var isOnDashBoard = false
    private val adsViewModel: AdsViewModel by viewModel()
    private val sharePreference: SharePreference by inject()
    private val leaderBoardViewModel: LeaderBoardViewModel by viewModel()
    override fun initView(
        savedInstanceState: Bundle?,
        binding: ActivityMainBinding,
    ) {
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
            ViewCompat.onApplyWindowInsets(view, windowInsets)
        }
        MobileAds.initialize(this) {}
        adsViewModel.scheduleShowAds(this)
        configAppSetting()
        leaderBoardViewModel.getTopLeaders()
    }

    private fun configAppSetting() {
        AppConfig.enableSoundEffect =
            !sharePreference.getBooleanPref(Constants.DISABLE_SOUND_EFFECT)
        AppConfig.enableVibrate = !sharePreference.getBooleanPref(Constants.DISABLE_VIBRATE)
        val background = sharePreference.getStringPref(Constants.BACKGROUND_URL)
        if (!background.isNullOrEmpty()) {
            AppConfig.background = background
        }
        if (AppConfig.enableSoundEffect) {
            ClickableViewExtensions.initSoundEffect()
        }
        Socket.channel = navigator.navigation
        leaderBoardViewModel.leaders.observe(this) {
            if (it is ResponseStatus.Success) {
                if ((it.data.data?.size ?: 0) >= 10) {
                    AppConfig.showedLeaderBoard = true
                }
            }
        }
    }

    override fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is DashboardEvent -> playSound()
            is MusicEvent -> if (event.enable) playSound() else pauseMusic()
            is ReportEvent -> {
                startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("https://m.me/dtako.dev")
                    },
                )
            }

            is NewsEvent -> {
                binding.news = event.news
                binding.ctnMotion.visible()
                binding.ctnMotion.transitionToEnd {
                    binding.ctnMotion.progress = 0F
                    binding.ctnMotion.invisible()
                }
            }

            else -> super.onNavigationEvent(event)
        }
    }

    private fun playSound() {
        try {
            if (mediaPlayer == null) {
                val enableMusic = !sharePreference.getBooleanPref(Constants.DISABLE_MUSIC)
                if (enableMusic) {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setDataSource("https://koai-dev.github.io/assets/raw/music/music.mp3")
                    isOnDashBoard = true
                    mediaPlayer?.isLooping = true
                    mediaPlayer?.prepareAsync()
                    mediaPlayer?.setOnPreparedListener {
                        it.start()
                    }
                }
            } else {
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun pauseMusic() {
        try {
            mediaPlayer?.pause()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        pauseMusic()
    }

    override fun onStop() {
        super.onStop()
        try {
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isOnDashBoard) {
            playSound()
        }
    }
}
