package com.koai.vocabvoyage.ui.dialog.news

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.core.os.bundleOf
import com.koai.base.main.action.router.BaseRouter
import com.koai.base.main.extension.navigatorViewModel
import com.koai.base.main.screens.BaseDialog
import com.koai.vocabvoyage.MainNavigator
import com.koai.vocabvoyage.R
import com.koai.vocabvoyage.databinding.DialogNewsBinding

class NewsDialog : BaseDialog<DialogNewsBinding, BaseRouter, MainNavigator>(R.layout.dialog_news) {
    override val navigator: MainNavigator by navigatorViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TopSheetDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            dialog.window?.setGravity(Gravity.TOP or Gravity.START)
        }
        return dialog
    }

    override fun initView(savedInstanceState: Bundle?, binding: DialogNewsBinding) {
        binding.news = arguments?.let { getBundle(it) }
        binding.root.postDelayed({ dismiss() }, 4000)
    }

    companion object {
        private const val NEWS = "news"
        fun createBundle(news: String) = bundleOf(NEWS to news)
        fun getBundle(bundle: Bundle) = bundle.getString(NEWS)
    }
}