package com.english.vocab.ui.theme

import android.os.Bundle
import android.widget.Toast
import com.english.vocab.MainNavigator
import com.english.vocab.R
import com.english.vocab.databinding.ScreenCustomThemeBinding
import com.english.vocab.domain.models.Background
import com.english.vocab.ui.theme.widget.BackgroundAdapter
import com.english.vocab.utils.AppConfig
import com.koai.base.main.adapter.BaseListAdapter
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.extension.screenViewModel
import com.koai.base.main.screens.BaseScreen
import com.koai.base.network.ResponseStatus

class CustomThemeScreen :
    BaseScreen<ScreenCustomThemeBinding, CustomThemeRouter, MainNavigator>(R.layout.screen_custom_theme) {
    override val navigator: MainNavigator by navigatorViewModel()
    private val viewModel: CustomThemeViewModel by screenViewModel()
    private lateinit var adapter: BackgroundAdapter

    override fun initView(
        savedInstanceState: Bundle?,
        binding: ScreenCustomThemeBinding,
    ) {
        if (AppConfig.background.isNullOrEmpty()) {
            binding.imgBg.loadImage(R.drawable.bg_home)
        } else {
            binding.img = AppConfig.background
        }
        binding.btnClose.setClickableWithScale(enableSoundEffect = AppConfig.enableSoundEffect) {
            router?.onPopScreen()
        }
        adapter = BackgroundAdapter()
        adapter.listener =
            object : BaseListAdapter.Action<Background> {
                override fun click(
                    position: Int,
                    data: Background,
                    code: Int,
                ) {
                    if (code == 1) {
                        router?.setBackground(data.link)
                        viewModel.saveSetting(data.link)
                        binding.img = data.link
                    } else {
                        Toast.makeText(
                            activity,
                            "Ohhh, you are not eligible to use this 😰",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        binding.rcv.adapter = adapter

        observer()
        getData()
    }

    private fun getData() {
        viewModel.getBackgrounds()
    }

    private fun observer() {
        viewModel.backgrounds.observe(this) {
            if (it is ResponseStatus.Success) {
                adapter.submitList(it.data.data)
            }
        }
    }
}
