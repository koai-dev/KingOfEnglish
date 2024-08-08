package com.english.vocab.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.english.vocab.R
import com.english.vocab.databinding.LayoutShareMySelfBinding
import com.english.vocab.domain.account.AccountUtils
import com.english.vocab.utils.AppConfig
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.utils.ScreenUtils

class ShareView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
        private val shareBinding: LayoutShareMySelfBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layout_share_my_self,
                this,
                true,
            )

        init {
            updateUI()
            this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }

        @SuppressLint("StringFormatMatches")
        fun updateUI() {
            shareBinding.user = AccountUtils.user
            if (AppConfig.background != null) {
                AppConfig.background?.let { shareBinding.imageView34.loadImage(AppConfig.background!!) }
            } else {
                shareBinding.imageView34.loadImage(R.drawable.bg_congratutation)
            }
            AccountUtils.user?.avatar?.let { shareBinding.imageView37.loadImage(it) }
            shareBinding.txtTitle.text =
                if ((AccountUtils.user?.currentLevel ?: 0) <= 1) {
                    resources.getString(R.string.you_can_do_better_try_harder)
                } else {
                    String.format(
                        resources.getString(R.string.congratulations_let_s_review_the_results_you_have_achieved),
                        AccountUtils.user?.name ?: "No Name",
                        AccountUtils.user?.currentLevel ?: 0,
                        AccountUtils.user?.points ?: 0,
                        AccountUtils.user?.top ?: 10,
                    )
                }
        }

        override fun onMeasure(
            widthMeasureSpec: Int,
            heightMeasureSpec: Int,
        ) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            setMeasuredDimension(
                ScreenUtils.getScreenWidth(context),
                ScreenUtils.getScreenWidth(context) * 9 / 20,
            )
        }
    }
