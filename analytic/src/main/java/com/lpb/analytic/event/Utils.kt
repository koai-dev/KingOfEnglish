package com.lpb.analytic.event

import android.annotation.SuppressLint
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

private fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}

fun String?.cleanValue(): String? = this?.unaccent()
    ?.replace(" ", "_")
    ?.lowercase()
    ?.replace("__", "_")
    ?.replace("__", "_")?.replace("đ", "d")
    ?.trim()


@SuppressLint("SimpleDateFormat")
fun Long.convertToDateTime(): String? {
    try {
        val date = Date(this)
        val format = SimpleDateFormat("MMM dd, yyyy HH:mm:ss")
        return format.format(date)
    } catch (e: Exception) {
        return null
    }
}
