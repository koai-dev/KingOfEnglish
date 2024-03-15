package com.koai.kingofenglish.ui.leaderboard

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenLeaderBoardBinding
import com.koai.kingofenglish.network.ApiController
import com.koai.kingofenglish.network.DataTopRanks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream


class LeaderBoardScreen :
    BaseScreen<ScreenLeaderBoardBinding, LeaderboardRouter, MainNavigator>(R.layout.screen_leader_board) {
    private lateinit var adapter: LeaderboardAdapter
    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
    override fun initView(savedInstanceState: Bundle?, binding: ScreenLeaderBoardBinding) {
        setupViews()
    }

    private fun setupViews() {
        val apiController = ApiController()
        val topRanksApi = apiController.getService(requireContext())
        if (topRanksApi != null) {
            val call = topRanksApi.getTopsRank(1)
            call.enqueue(object : Callback<DataTopRanks> {
                override fun onResponse(
                    call: Call<DataTopRanks>,
                    response: Response<DataTopRanks>
                ) {
                    if (response.isSuccessful) {
                        val topRanksResponse = response.body()
                        if (topRanksResponse != null) {
                            val questionData = topRanksResponse.data
                            adapter = LeaderboardAdapter(questionData)
                            binding.recyclerViewHighRank.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.recyclerViewHighRank.adapter = adapter
                            binding.txtBotLevel.text = questionData!![19]!!.currentLevel.toString()
                            Glide.with(requireContext()).load(questionData[19]!!.avatar)
                                .into(binding.imgBotAvt)
                            binding.txtBotName.text = questionData[19]!!.name.toString()
                            binding.txtBotScore.text = questionData[19]!!.points.toString()
                        }
                    }
                }

                override fun onFailure(call: Call<DataTopRanks>, t: Throwable) {
                }
            })
        }
        binding.ctnBtnShare.setClickableWithScale {
            captureAndSaveScreenshot()
        }
    }

    private fun captureAndSaveScreenshot() {
        val screenShotBitmap = captureScreen()
        if (screenShotBitmap != null) {
            saveScreenshotToStorage(screenShotBitmap)
        } else {
            Toast.makeText(requireContext(), "Failed to capture screen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun captureScreen(): Bitmap? {
        val rootView = activity.window.decorView.rootView
        val screenShotBitmap =
            Bitmap.createBitmap(rootView.width, rootView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenShotBitmap)
        rootView.draw(canvas)
        return screenShotBitmap
    }

    private fun saveScreenshotToStorage(bitmap: Bitmap) {
        val screenshotDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Screenshots"
        )
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs()
        }
        val fileName = "screenshot_${System.currentTimeMillis()}.png"
        val file = File(screenshotDir, fileName)
        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            Toast.makeText(
                requireContext(),
                "Screenshot saved to ${file.absolutePath}",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to save screenshot", Toast.LENGTH_SHORT).show()
        }
    }
}

