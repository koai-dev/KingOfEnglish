package com.lpb.analytic.event

import java.text.Normalizer

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
