package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding

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
        val height = binding.imageRounded.height
        val width = binding.imageRounded.width
        when (clickCount) {
            0 -> {
                val tranXRounded = binding.icDiamondTutorial.right - 15
                val tranYRounded = binding.txtDiamondTutorial.top - 22
                updateUI(
                    "Your score",
                    R.drawable.ic_arrow_2,
                    tranXRounded,
                    tranYRounded,
                    tranXRounded,
                    tranYRounded
                )
                binding.imageRounded.layoutParams.height = height + 15
                binding.imageRounded.layoutParams.width = width + 15
                binding.imageRounded.requestLayout()
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 1f
                binding.icDiamondTutorial.alpha = 1f
                binding.textView4.translationX = tranXRounded.toFloat() - 50
                binding.logoToturial.translationX = binding.textView4.translationX
            }

            1 -> {
                val tranXRounded = binding.txtCrownTutorial.right -27
                val tranYRounded = binding.txtCrownTutorial.top - 25
                val tranXArrow = tranXRounded - 100
                val tranYArrow = tranYRounded - 30
                updateUI(
                    "Your level",
                    R.drawable.ic_arrow_3,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                binding.imageRounded.layoutParams.height = height + 25
                binding.imageRounded.layoutParams.width = width + 25
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 1f
                binding.textView4.translationX = tranXArrow.toFloat() - 50
                binding.logoToturial.translationX = binding.textView4.translationX
            }
            2 -> {
                val tranXRounded = binding.btnPlayNowTutorial.right / 2
                val tranYRounded = binding.btnPlayNowTutorial.top
                val tranXArrow = binding.btnPlayNowTutorial.right / 2
                val tranYArrow = binding.btnPlayNowTutorial.top - binding.imageArrow.height - 80
                updateUI(
                    "Click here to play",
                    R.drawable.ic_arrow_4,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial_2)
                binding.imageRounded.visibility = View.GONE
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 1f
                binding.textView4.translationY = tranYArrow.toFloat() - binding.imageArrow.height -40
                binding.textView4.translationX = tranXArrow.toFloat() - 100
                binding.logoToturial.translationX = binding.textView4.left.toFloat() - 90
                binding.logoToturial.translationY = tranYArrow.toFloat() - binding.imageArrow.height - 60
            }
            3 -> {
                val tranXRounded = binding.btnPlayNowTutorial.right / 2
                val tranYRounded = binding.btnPlayNowTutorial.top
                val tranXArrow = binding.btnPlayNowTutorial.right / 2
                val tranYArrow = binding.btnPlayNowTutorial.top - binding.imageArrow.height - 80
                updateUI(
                    "Click To See Ranking Position",
                    R.drawable.ic_arrow_5,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial_2)
                binding.imageRounded.visibility = View.GONE
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 0f
                binding.btnLeaderBoardTutorial.alpha = 1f
                binding.textView4.translationY = tranYArrow.toFloat() - binding.imageArrow.height -40
                binding.textView4.translationX = tranXArrow.toFloat() - 100
                binding.logoToturial.translationX = binding.textView4.left.toFloat() - 90
                binding.logoToturial.translationY = tranYArrow.toFloat() - binding.imageArrow.height - 60
            }
            4 -> {
                val tranXRounded = binding.btnPlayNowTutorial.right / 2
                val tranYRounded = binding.btnPlayNowTutorial.top
                val tranXArrow = binding.btnPlayNowTutorial.right / 2
                val tranYArrow = binding.btnPlayNowTutorial.top - binding.imageArrow.height - 80
                updateUI(
                    "Custom theme according to your personality",
                    R.drawable.ic_arrow_6,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial_2)
                binding.imageRounded.visibility = View.GONE
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 0f
                binding.btnLeaderBoardTutorial.alpha = 0f
                binding.ctnDownTutorial.alpha = 1f
                binding.imgCustomTutorial.alpha = 1f
                binding.textView4.translationY = tranYArrow.toFloat() - binding.imageArrow.height -40
                binding.textView4.translationX = tranXArrow.toFloat() - 100
                binding.logoToturial.translationX = binding.textView4.left.toFloat() - 90
                binding.logoToturial.translationY = tranYArrow.toFloat() - binding.imageArrow.height - 60
            }
            5 -> {
                val tranXRounded = binding.btnPlayNowTutorial.right / 2
                val tranYRounded = binding.btnPlayNowTutorial.top
                val tranXArrow = binding.btnPlayNowTutorial.right / 2
                val tranYArrow = binding.btnPlayNowTutorial.top - binding.imageArrow.height - 80
                updateUI(
                    "Click here to set  the mode",
                    R.drawable.ic_arrow_7,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial_2)
                binding.imageRounded.visibility = View.GONE
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 0f
                binding.btnLeaderBoardTutorial.alpha = 0f
                binding.imgCustomTutorial.alpha = 0f
                binding.ctnDownTutorial.alpha = 1f
                binding.imgSettingTutorial.alpha = 1f
                binding.textView4.translationY = tranYArrow.toFloat() - binding.imageArrow.height -40
                binding.textView4.translationX = tranXArrow.toFloat() - 100
                binding.logoToturial.translationX = binding.textView4.left.toFloat() - 90
                binding.logoToturial.translationY = tranYArrow.toFloat() - binding.imageArrow.height - 60
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
        binding.imageArrow.translationX =translationXArrow.toFloat()
        binding.imageArrow.translationY =translationYArrow.toFloat()
        binding.imageRounded.translationX = translationXRounded.toFloat()
        binding.imageRounded.translationY = translationYRounded.toFloat()

    }

    private fun resetUI() {
        binding.motionLayout.removeView(binding.motionLayout2)
    }
    override fun getModelNavigator()= ViewModelProvider(activity)[MainNavigator::class.java]
}