package com.english.vocab.ui.play.widget

import com.english.vocab.R
import com.english.vocab.databinding.ItemLetterResultBinding
import com.english.vocab.databinding.ItemLetterUnderBinding
import com.koai.base.main.adapter.BaseListAdapter
import com.koai.base.main.extension.ClickableViewExtensions.setClickableWithScale
import com.english.vocab.utils.AppConfig

class LetterAdapter(private val type: TypeLetter = TypeLetter.TYPE_QUESTION) :
    BaseListAdapter<Letter>() {
    override fun getLayoutId() =
        if (type == TypeLetter.TYPE_QUESTION) R.layout.item_letter_under else R.layout.item_letter_result

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
    ) {
        if (type == TypeLetter.TYPE_QUESTION) {
            (holder.binding as ItemLetterUnderBinding).apply {
                letter = getItem(holder.bindingAdapterPosition)
                root.setClickableWithScale(
                    enableSoundEffect = AppConfig.enableSoundEffect,
                    delayTimeDoubleClick = 100
                ) {
                    try {
                        listener?.click(
                            holder.bindingAdapterPosition,
                            getItem(holder.bindingAdapterPosition),
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                executePendingBindings()
            }
        } else {
            (holder.binding as ItemLetterResultBinding).apply {
                letter = getItem(holder.bindingAdapterPosition)
                root.setClickableWithScale(
                    enableSoundEffect = AppConfig.enableSoundEffect,
                    delayTimeDoubleClick = 100
                ) {
                    try {
                        listener?.click(
                            holder.bindingAdapterPosition,
                            getItem(holder.bindingAdapterPosition),
                            1,
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                executePendingBindings()
            }
        }
    }
}

enum class TypeLetter {
    TYPE_QUESTION,
    TYPE_ANSWER,
}
