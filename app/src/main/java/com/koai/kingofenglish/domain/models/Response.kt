package com.koai.kingofenglish.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Response<T>(

    @field:SerializedName("data")
    val data: Class<T>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null
) : Parcelable
