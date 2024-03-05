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
        binding.ctnTutorial.setOnClickListener {
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

        when (clickCount) {
            0 -> {
                binding.ctnTutorial1.visibility = View.INVISIBLE
                binding.imgArrow2.visibility = View.VISIBLE
                binding.txtDiamondTutorial.visibility = View.VISIBLE
                binding.txtTutorial2.visibility = View.VISIBLE
                binding.logoTutorial2.visibility = View.VISIBLE
                binding.imgRounded2.visibility = View.VISIBLE
            }

            1 -> {
                binding.imgArrow2.visibility = View.INVISIBLE
                binding.txtTutorial2.visibility = View.INVISIBLE
                binding.logoTutorial2.visibility = View.INVISIBLE
                binding.imgRounded2.visibility = View.INVISIBLE
                binding.txtDiamondTutorial.visibility = View.INVISIBLE
                binding.imgArrow3.visibility = View.VISIBLE
                binding.txtTutorial3.visibility = View.VISIBLE
                binding.logoTutorial3.visibility = View.VISIBLE
                binding.imgRounded3.visibility = View.VISIBLE
                binding.txtCrownTutorial.visibility = View.VISIBLE
            }

            2 -> {
                binding.imgArrow3.visibility = View.INVISIBLE
                binding.txtTutorial3.visibility = View.INVISIBLE
                binding.logoTutorial3.visibility = View.INVISIBLE
                binding.imgRounded3.visibility = View.INVISIBLE
                binding.txtCrownTutorial.visibility = View.INVISIBLE
                binding.imgArrow4.visibility = View.VISIBLE
                binding.txtTutorial4.visibility = View.VISIBLE
                binding.logoTutorial4.visibility = View.VISIBLE
                binding.btnPlayNowTutorial.visibility = View.VISIBLE
            }

            3 -> {
                binding.imgArrow4.visibility = View.INVISIBLE
                binding.txtTutorial4.visibility = View.INVISIBLE
                binding.logoTutorial4.visibility = View.INVISIBLE
                binding.btnPlayNowTutorial.visibility = View.INVISIBLE
                binding.imgArrow5.visibility = View.VISIBLE
                binding.txtTutorial5.visibility = View.VISIBLE
                binding.logoTutorial5.visibility = View.VISIBLE
                binding.btnLeaderBoardTutorial.visibility = View.VISIBLE
            }

            4 -> {

                binding.imgArrow5.visibility = View.INVISIBLE
                binding.txtTutorial5.visibility = View.INVISIBLE
                binding.logoTutorial5.visibility = View.INVISIBLE
                binding.btnLeaderBoardTutorial.visibility = View.INVISIBLE
                binding.ctnTutorial6.visibility = View.VISIBLE
            }

            5 -> {
                binding.ctnTutorial6.visibility = View.INVISIBLE
                binding.ctnTutorial7.visibility = View.VISIBLE
            }

            else -> resetUI()
        }
        clickCount++

    }

    private fun resetUI() {
        binding.ctnHome.removeView(binding.ctnTutorial)
        firstLogin = true
        SharePreference.setBooleanPref(requireContext(),"FirstLogin",firstLogin)
    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}