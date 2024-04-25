package com.koai.kingofenglish.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.koai.base.utils.ScreenUtils
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.LayoutShareMySelfBinding
import com.koai.kingofenglish.domain.account.AccountUtils
import com.koai.kingofenglish.utils.AppConfig

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
        updateUI()
        this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun updateUI(){
        shareBinding.user = AccountUtils.user
        shareBinding.img = AppConfig.background
        shareBinding.txtTitle.text = if ((AccountUtils.user?.currentLevel ?: 0) <= 1) {
            resources.getString(R.string.you_can_do_better_try_harder)
        } else
            String.format(
                resources.getString(R.string.congratulations_let_s_review_the_results_you_have_achieved),
                AccountUtils.user?.name ?: "No Name",
                AccountUtils.user?.currentLevel ?: 0,
                AccountUtils.user?.points ?: 0,
                AccountUtils.user?.top ?: 10
            )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            ScreenUtils.getScreenWidth(context),
            ScreenUtils.getScreenWidth(context) * 9 / 20
        )
    }

}