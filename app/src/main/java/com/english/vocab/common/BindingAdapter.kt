package com.english.vocab.common

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.koai.base.main.extension.ClickableViewExtensions.loadImage
import com.koai.base.main.extension.gone
import com.koai.base.main.extension.visible
import com.english.vocab.utils.arrayToString

@BindingAdapter("loadImage")
fun loadImage(
    img: ImageView,
    source: Any?,
) {
    source?.let {
        img.loadImage(source, onSuccess = {}, onFail = {})
    }
}

@BindingAdapter(
    value = ["text_app", "color_enable", "color_disable", "stateEnable", "allowNullTextVisible"],
    requireAll = false,
)
fun setText(
    txt: TextView,
    text: Any?,
    colorEnable: Any? = null,
    colorDisable: Any? = null,
    enable: Boolean = true,
    allowNullTextVisible: Boolean = false,
) {
    if (!allowNullTextVisible && text == null) {
        txt.text = ""
    } else {
        text?.let {
            txt.text = it.toString()
        }
    }
    if (text is String?) {
        if (text?.lowercase()?.contains("null") == true) {
            if (!allowNullTextVisible) {
                txt.gone()
            }
        }
    }
    if (allowNullTextVisible) {
        txt.visible()
    }
    if (enable) {
        if (colorEnable != null) {
            txt.setColor(colorEnable)
        }
    } else {
        if (colorDisable != null) {
            txt.setColor(colorDisable)
        }
    }
}

private fun TextView.setColor(color: Any) {
    when (color) {
        is String -> this.setTextColor(Color.parseColor(color))
        is Int -> this.setTextColor(color)
        else -> Unit
    }
}

@BindingAdapter("array_to_string")
fun arrayToString(
    txt: TextView,
    list: ArrayList<String?>?,
) {
    list?.let {
        txt.text = list.arrayToString()
    }
}
