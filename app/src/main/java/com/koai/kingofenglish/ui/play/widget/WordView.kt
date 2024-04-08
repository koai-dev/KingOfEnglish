package com.koai.kingofenglish.ui.play.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.koai.base.main.adapter.BaseListAdapter
import com.koai.kingofenglish.R
import com.koai.kingofenglish.databinding.LayoutWordViewBinding

class WordView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var type: String = "type_question"
    private var adapter: LetterAdapter
    private var binding: LayoutWordViewBinding =
        LayoutWordViewBinding.inflate(LayoutInflater.from(context), this, true)

    var action: Action? = null
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WordView)
        type = typedArray.getString(R.styleable.WordView_type) ?: ""
        typedArray.recycle()
        adapter = LetterAdapter(if (type=="1") TypeLetter.TYPE_QUESTION else TypeLetter.TYPE_ANSWER)
        adapter.listener = object : BaseListAdapter.Action<Letter>{
            override fun click(position: Int, data: Letter, code: Int) {
                action?.onClick(position, data, code)
            }

        }
        binding.rcv.layoutManager = FlexboxLayoutManager(context, FlexDirection.ROW).apply {
            justifyContent = JustifyContent.CENTER
        }
        binding.rcv.adapter = adapter
    }

    fun submitList(letters: List<Letter?>){
        adapter.submitList(letters)
    }

    fun getAdapter() = adapter

    interface Action{
        fun onClick(position: Int, data: Letter, code: Int)
    }
}