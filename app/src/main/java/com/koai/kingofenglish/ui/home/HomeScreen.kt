package com.koai.kingofenglish.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.invisible
import com.koai.base.main.extension.visible
import com.koai.base.main.screens.BaseScreen
import com.koai.base.utils.SharePreference
import com.koai.kingofenglish.MainNavigator
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.ScreenHomeBinding
import com.koai.kingofenglish.network.QuestionViewModel
import kotlinx.coroutines.launch

class HomeScreen : BaseScreen<ScreenHomeBinding, HomeRouter, MainNavigator>(R.layout.screen_home) {
    private val viewModel : QuestionViewModel by viewModels()
    private var clickCount = 0
    private var firstLogin = false
    private var textAnswer : String = ""

    override fun initView(savedInstanceState: Bundle?, binding: ScreenHomeBinding) {
        binding.txtCrown.text = SharePreference.getIntPref(requireContext(),"LEVEL").toString()
        val isFirstLogin = SharePreference.getBooleanPref(requireContext(), "FirstLogin")
         if (isFirstLogin) {
            binding.ctnHome.removeView(binding.ctnTutorial)
        }
        actionView()
        getData()
    }
    private fun getData() {
        viewModel.getQuestionByLevel(binding.txtCrown.text.toString().toInt(),requireContext())
        lifecycleScope.launch {
            viewModel.questionStateFlow.collect {
                if (it != null) {
                    textAnswer = it.data?.answerRight.toString()
                }
            }
        }

    }

    private fun actionView() {
        binding.ctnTutorial.setOnClickListener {
            onConstraintLayoutClick()
        }
        binding.btnPlayNow.setClickableWithScale {
            val bundle = Bundle().apply {
                putString("QUESTION", textAnswer)
            }
            router?.goToPlay(bundle)
        }
        binding.btnLeaderBoard.setClickableWithScale {
            router?.goToLeaderBoard()
        }
        binding.imgUser.setClickableWithScale {
            router?.dialogProfile()
        }
        binding.imgSetting.setClickableWithScale {
            router?.dialogSetting()
        }
        binding.imgCustom.setClickableWithScale {
            binding.ctnHome.transitionToState(R.id.theme)
        }
        binding.ctnCustomTheme.btnClose.setClickableWithScale {
            binding.ctnHome.transitionToState(R.id.end)
        }
        handleLevelVisibility(binding.txtCrown.text.toString().toInt())
        binding.ctnCustomTheme.bgLv10.setClickableWithScale {
            binding.ctnHome.transitionToState(R.id.chage_theme)
            binding.imgBgHome.setBackgroundResource(R.drawable.bg_lv10)
        }
        binding.ctnCustomTheme.bgLv20.setClickableWithScale {
            binding.imgBgHome.setBackgroundResource(R.drawable.bg_lv20)
        }
        binding.ctnCustomTheme.bgLv30.setClickableWithScale {
            binding.imgBgHome.setBackgroundResource(R.drawable.bg_lv30)
        }
        binding.ctnCustomTheme.bgLv40.setClickableWithScale {
            binding.imgBgHome.setBackgroundResource(R.drawable.bg_lv40)
        }
    }

    private fun handleLevelVisibility(level: Int) {
        when {
            level >= 40 -> {
                binding.ctnCustomTheme.imgLookLv40.gone()
                binding.ctnCustomTheme.viewLv40.gone()
            }
            level >= 30 -> {
                binding.ctnCustomTheme.imgLookLv30.gone()
                binding.ctnCustomTheme.viewLv30.gone()
            }
            level >= 20 -> {
                binding.ctnCustomTheme.imgLookLv20.gone()
                binding.ctnCustomTheme.viewLv20.gone()
            }
            level >= 10 -> {
                binding.ctnCustomTheme.imgLookLv10.gone()
                binding.ctnCustomTheme.viewLv10.gone()
            }
        }
    }

    private fun onConstraintLayoutClick() {

        when (clickCount) {
            0 -> {
                binding.ctnTutorial1.invisible()
                binding.imgArrow2.visible()
                binding.txtDiamondTutorial.visible()
                binding.txtTutorial2.visible()
                binding.logoTutorial2.visible()
                binding.imgRounded2.visible()
            }

            1 -> {
                binding.imgArrow2.invisible()
                binding.txtTutorial2.invisible()
                binding.logoTutorial2.invisible()
                binding.imgRounded2.invisible()
                binding.txtDiamondTutorial.invisible()
                binding.imgArrow3.visible()
                binding.txtTutorial3.visible()
                binding.logoTutorial3.visible()
                binding.imgRounded3.visible()
                binding.txtCrownTutorial.visible()
            }

            2 -> {
                binding.imgArrow3.invisible()
                binding.txtTutorial3.invisible()
                binding.logoTutorial3.invisible()
                binding.imgRounded3.invisible()
                binding.txtCrownTutorial.invisible()
                binding.imgArrow4.visible()
                binding.txtTutorial4.visible()
                binding.logoTutorial4.visible()
                binding.btnPlayNowTutorial.visible()
            }

            3 -> {
                binding.imgArrow4.invisible()
                binding.txtTutorial4.invisible()
                binding.btnPlayNowTutorial.invisible()
                binding.logoTutorial4.invisible()
                binding.imgArrow5.visible()
                binding.txtTutorial5.visible()
                binding.logoTutorial5.visible()
                binding.btnLeaderBoardTutorial.visible()
            }

            4 -> {

                binding.imgArrow5.invisible()
                binding.txtTutorial5.invisible()
                binding.logoTutorial5.invisible()
                binding.btnLeaderBoardTutorial.invisible()
                binding.ctnTutorial6.visible()
            }

            5 -> {
                binding.ctnTutorial6.invisible()
                binding.ctnTutorial7.visible()
            }

            else -> resetUI()
        }
        clickCount++

    }

    private fun resetUI() {
        binding.ctnHome.removeView(binding.ctnTutorial)
        firstLogin = true
        SharePreference.setBooleanPref(requireContext(), "FirstLogin", firstLogin)
    }

    override fun getModelNavigator() = ViewModelProvider(activity)[MainNavigator::class.java]
}