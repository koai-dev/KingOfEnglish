package com.koai.kingofenglish.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.koai.base.utils.ScreenUtils
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.LayoutShareMySelfBinding
import com.koai.kingofenglish.domain.account.AccountUtils

class ShareView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val shareBinding: LayoutShareMySelfBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_share_my_self,
        this,
        true
    )

    init {
        shareBinding.user = AccountUtils.user
        this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenWidth(context)*9/20)
    }

}