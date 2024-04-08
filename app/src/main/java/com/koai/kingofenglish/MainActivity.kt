package com.koai.kingofenglish

import android.media.MediaPlayer
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.BaseActivity
import com.koai.base.main.action.event.NavigationEvent
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.kingofenglish.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity :
    BaseActivity<ActivityMainBinding, BaseRouter, MainNavigator>(R.layout.activity_main) {
    override val navigator: MainNavigator by viewModel()
    private var mediaPlayer: MediaPlayer? = null
    private var isOnDashBoard = false
    override fun initView(savedInstanceState: Bundle?, binding: ActivityMainBinding) {

    }

    override fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is DashboardEvent -> playSound()
            else -> super.onNavigationEvent(event)
        }

    }

    private fun playSound() {
        try {
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource("https://koai-dev.github.io/assets/raw/music/music.mp3")
                isOnDashBoard = true
                mediaPlayer?.isLooping = true
                mediaPlayer?.prepareAsync()
                mediaPlayer?.setOnPreparedListener {
                    it.start()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            mediaPlayer?.pause()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            mediaPlayer?.release()
            mediaPlayer = null
        }catch (e: Exception){
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
