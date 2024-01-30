package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding
import kotlinx.coroutines.delay

class HomeScreen : BaseScreen<ScreenHomeBinding,HomeRouter,MainNavigator>(R.layout.screen_home) {
    private var clickCount = 0

    override fun initView(savedInstanceState: Bundle?, binding: ScreenHomeBinding) {
        actionView()

    }

    private fun actionView() {
        binding.motionLayout2.setOnClickListener {
            onConstraintLayoutClick()
        }
        binding.btnPlayNow.setClickableWithScale{
            router?.goToPlay()
        }
    }

    private fun onConstraintLayoutClick() {
        when (clickCount) {
            0 -> {
                updateUI(
                    "Your score",
                    R.drawable.ic_arrow_2,
                    R.dimen._144dp,
                    R.dimen._0dp,
                    R.dimen._150dp,
                    R.dimen._30dp
                )
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 1f
                binding.icDiamondTutorial.alpha = 1f
            }

            1 -> {updateUI(
                "Your level",
                R.drawable.ic_arrow_3,
                R.dimen._175dp,
                R.dimen._0dp,
                R.dimen._275dp,
                R.dimen._30dp
            )
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 1f
            }
            else -> resetUI()
        }
        clickCount++

    }

    private fun updateUI(
        text: String,
        imageResId: Int,
        translationXArrow: Int,
        translationYArrow: Int,
        translationXRounded: Int,
        translationYRounded: Int
    ) {
        binding.textView4.text = text
        binding.imageArrow.setImageResource(imageResId)

        binding.imageArrow.translationX =
            resources.getDimensionPixelSize(translationXArrow).toFloat()
        binding.imageArrow.translationY =
            resources.getDimensionPixelSize(translationYArrow).toFloat()
        binding.imageRounded.translationX =
            resources.getDimensionPixelSize(translationXRounded).toFloat()
        binding.textView4.translationX = resources.getDimensionPixelSize(translationXArrow).toFloat()
        binding.logoToturial.translationX = binding.textView4.translationX
    }

    private fun resetUI() {
        binding.motionLayout.removeView(binding.motionLayout2)
    }
    override fun getModelNavigator()= ViewModelProvider(activity)[MainNavigator::class.java]
}