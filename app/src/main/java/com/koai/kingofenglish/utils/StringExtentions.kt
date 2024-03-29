package com.koai.kingofenglish.utils

fun String?.stringToArray(): ArrayList<String>{
    val list = arrayListOf<String>()
    val strings =  this?.split(",")
    strings?.forEach {
        if(it.trim().isNotEmpty()) list.add(it.trim())
    }
    return list
}

fun ArrayList<String?>?.arrayToString(): String{
    var string = ""
    this?.forEach {
        it?.let {
            string +="$it/"
        }
    }
    string = string.replaceBeforeLast("/","")
    return string
}