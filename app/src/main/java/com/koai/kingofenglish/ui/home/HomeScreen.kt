package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    private var clickCount = 0
    private var firstLogin = false

    override fun initView(savedInstanceState: Bundle?, binding: ScreenHomeBinding) {
        val isFirstLogin = SharePreference.getBooleanPref(requireContext(),"FirstLogin",)
        if (isFirstLogin == false){

        }else if (isFirstLogin == true){

        }
        actionView()
        Toast.makeText(requireContext(), "$isFirstLogin", Toast.LENGTH_SHORT).show()
    }

    private fun actionView() {
        binding.motionLayout2.setOnClickListener {
            onConstraintLayoutClick()
        }
        binding.btnPlayNow.setClickableWithScale {
            router?.goToPlay()
        }
        binding.imgUser.setClickableWithScale {
            router?.dialogProfile()
        }
        binding.imgSetting.setClickableWithScale {
            router?.dialogSetting()
        }
    }

    private fun onConstraintLayoutClick() {
        val height = binding.imageRounded.height
        val width = binding.imageRounded.width
        val constraintSet = ConstraintSet()
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
                val tranXRounded = binding.txtCrownTutorial.right - 27
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
                binding.textView4.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height - 40
                binding.textView4.translationX = tranXArrow.toFloat() - 100
                binding.logoToturial.translationX = binding.textView4.left.toFloat() - 90
                binding.logoToturial.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height - 60
            }

            3 -> {
                val tranXRounded = binding.btnPlayNowTutorial.right / 2
                val tranYRounded = binding.btnPlayNowTutorial.top
                val tranXArrow = binding.btnLeaderBoardTutorial.right / 2
                val tranYArrow =
                    binding.btnLeaderBoardTutorial.top - binding.imageArrow.height - 180
                updateUI(
                    "Click To See Ranking Position",
                    R.drawable.ic_arrow_5,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial_3)
                binding.imageRounded.visibility = View.GONE
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 0f
                binding.btnLeaderBoardTutorial.alpha = 1f
                binding.textView4.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height - 150
                binding.textView4.translationX = tranXArrow.toFloat() - 150
                binding.logoToturial.translationX = binding.textView4.right.toFloat() - 30
                binding.logoToturial.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height - 170
            }

            4 -> {
                val location = IntArray(2)
                binding.imgCustomTutorial.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1]
                val tranXRounded = x - 5
                val tranYRounded = y - 80
                val tranXArrow = x + 50
                val tranYArrow = y - binding.imageArrow.height - 50
                updateUI(
                    "Custom theme according to your personality",
                    R.drawable.ic_arrow_6,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                val density = resources.displayMetrics.density
                binding.imageRounded.layoutParams.height = height - 30
                binding.imageRounded.layoutParams.width = width - 30
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial_4)
                binding.logoToturial.layoutParams.height = (48 * density).toInt()
                binding.logoToturial.layoutParams.width = (48 * density).toInt()
                binding.imageRounded.visibility = View.VISIBLE
                binding.imageRounded.alpha = 1f
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 0f
                binding.btnLeaderBoardTutorial.alpha = 0f
                binding.ctnDownTutorial.alpha = 0.9f
                binding.imgCustomTutorial.alpha = 0.9f
                binding.textView4.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height + 100
                binding.textView4.translationX = tranXArrow.toFloat() - 200
                binding.logoToturial.translationX = binding.textView4.left.toFloat() - binding.textView4.width + (40 * density)
                binding.logoToturial.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height
            }

            5 -> {
                val location = IntArray(2)
                binding.imgSettingTutorial.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1]
                val tranXRounded = x + 10
                val tranYRounded = y - 80
                val tranXArrow = x + 50
                val tranYArrow = y - 2 * binding.imageArrow.height
                updateUI(
                    "Click here to set  the mode",
                    R.drawable.ic_arrow_7,
                    tranXArrow,
                    tranYArrow,
                    tranXRounded,
                    tranYRounded
                )
                constraintSet.clone(binding.motionLayout2)
                constraintSet.connect(
                    R.id.image_rounded,
                    ConstraintSet.START,
                    R.id.img_setting_tutorial,
                    ConstraintSet.START
                )
                constraintSet.applyTo(binding.motionLayout2)
                binding.logoToturial.setImageResource(R.drawable.logo_tutorial)
                binding.logoToturial.layoutParams.height = 48
                binding.logoToturial.layoutParams.width = 48
                binding.imageRounded.visibility = View.VISIBLE
                binding.imgUserTutorial.alpha = 0f
                binding.txtDiamondTutorial.alpha = 0f
                binding.icDiamondTutorial.alpha = 0f
                binding.txtCrownTutorial.alpha = 0f
                binding.btnPlayNowTutorial.alpha = 0f
                binding.btnLeaderBoardTutorial.alpha = 0f
                binding.imgCustomTutorial.alpha = 0f
                binding.ctnDownTutorial.alpha = 0.9f
                binding.imgSettingTutorial.alpha = 0.9f
                binding.textView4.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height - 40
                binding.textView4.translationX = tranXArrow.toFloat() - 200
                binding.logoToturial.translationX = binding.textView4.translationX
                binding.logoToturial.translationY =
                    tranYArrow.toFloat() - binding.imageArrow.height - 60
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
        binding.imageArrow.translationX = translationXArrow.toFloat()
        binding.imageArrow.translationY = translationYArrow.toFloat()
        binding.imageRounded.translationX = translationXRounded.toFloat()
        binding.imageRounded.translationY = translationYRounded.toFloat()

    }

    private fun resetUI() {
        binding.motionLayout.removeView(binding.motionLayout2)
        firstLogin = true
        SharePreference.setBooleanPref(requireContext(),"FirstLogin",firstLogin)
    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}