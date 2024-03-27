package com.koai.kingofenglish.common

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.koai.base.main.extension.ClickableViewExtensions.loadImage

@BindingAdapter("loadImage")
fun loadImage(img: ImageView, source: Any?) {
    source?.let {
        img.loadImage(source, onSuccess = {}, onFail = {})
    }
}

@BindingAdapter("text_app")
fun setText(txt: TextView, text: Any?) {
    when (text) {
        is Char? -> txt.text = (text ?: "").toString()
        is Int? -> txt.text = (text ?: 0).toString()
        is Long? -> txt.text = (text ?: 0).toString()
        is Short? -> txt.text = (text ?: 0).toString()
        is Double? -> txt.text = (text ?: 0).toString()
        is Float? -> txt.text = (text ?: 0).toString()
        else -> txt.text = (text ?: "").toString()
    }
}