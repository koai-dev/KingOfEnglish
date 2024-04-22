package com.koai.kingofenglish.utils

fun Int.convertNumber(): String {
    return if (this >= 1000000000) {
        ((this / 100000000L).toInt() / 10).toString() + "B"
    } else if (this >= 1000000) {
        ((this / 100000L).toInt() / 10).toString() + "M"
    } else if (this >= 1000) {
        ((this / 100L).toInt() / 10).toString() + "K"
    } else {
        this.toString()
    }
}