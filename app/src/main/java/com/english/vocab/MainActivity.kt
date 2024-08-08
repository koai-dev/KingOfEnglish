package com.english.vocab

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.english.vocab.ads.AdsViewModel
import com.english.vocab.databinding.ActivityMainBinding
import com.english.vocab.notification.MyMessaging
import com.english.vocab.service.Socket
import com.english.vocab.utils.AppConfig
import com.english.vocab.utils.Constants
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.firebase.messaging.RemoteMessage
import com.koai.base.main.BaseActivity
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.ClickableViewExtensions
import com.koai.base.main.extension.invisible
import com.koai.base.main.extension.visible
import com.koai.base.utils.SharePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<ActivityMainBinding, BaseRouter, MainNavigator>(R.layout.activity_main) {
    override val navigator: MainNavigator by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private var mediaPlayer: MediaPlayer? = null
    private var isOnDashBoard = false
    private val adsViewModel: AdsViewModel by viewModel()
    private val sharePreference: SharePreference by inject()
    private val notificationIntentFilter = IntentFilter(MyMessaging.KOE_NOTIFICATION)

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
        adsViewModel.scheduleShowAds(this)
        mainViewModel.checkLocale()
        configAppSetting()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            notificationReceiver,
            notificationIntentFilter,
        )
        setUpAdmobBanner()
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
                Log.d("NewsEvent: ", event.news)
                binding.news = event.news
                binding.ctnMotion.visible()
                binding.ctnMotion.progress = 0f
                binding.ctnMotion.transitionToEnd {
                    binding.ctnMotion.progress = 0f
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
        Log.d("MainActivity: ", "onPause")
        AppConfig.isForeground = false
    }

    override fun onDestroy() {
        Log.d("MainActivity: ", "onDestroy")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(notificationReceiver)
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity: ", "onStop")
        try {
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity: ", "onResume")
        AppConfig.isForeground = true
        if (isOnDashBoard) {
            playSound()
        }
        if (AppConfig.showPopupNotificationSetting) {
            val isFirstLaunch = !sharePreference.getBooleanPref(Constants.IS_FIRST_LAUNCH)
            if (isFirstLaunch) {
                navigator.gotoTutorial()
                sharePreference.setBooleanPref(Constants.IS_FIRST_LAUNCH, true)
            }
        }
    }

    private val notificationReceiver: BroadcastReceiver =
        object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent,
            ) {
                val data =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(MyMessaging.KEY_NOTIFICATION, RemoteMessage::class.java)
                    } else {
                        intent.getParcelableExtra(MyMessaging.KEY_NOTIFICATION)
                    }
                data?.data?.get(MyMessaging.BODY)?.let { news ->
                    navigator.sendEvent(NewsEvent("$news "))
                }
            }
        }

    private fun setUpAdmobBanner() {
        var adRequest: AdRequest? = null
        val adView = AdView(this)
        adView.adUnitId = BuildConfig.BANNER_ADS
        adView.setAdSize(AdSize.FULL_BANNER)
        binding.banner.addView(adView)
        CoroutineScope(Dispatchers.IO).launch {
            adRequest = AdRequest.Builder().build()
        }.invokeOnCompletion {
            runOnUiThread {
                adRequest?.let { it1 -> adView.loadAd(it1) }
            }
        }
    }
}
