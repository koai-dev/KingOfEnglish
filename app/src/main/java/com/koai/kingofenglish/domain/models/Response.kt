package com.koai.kingofenglish.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Response<T>(
    @field:SerializedName("data")
    val data: @RawValue T? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("statusCode")
    val statusCode: Int? = null,
) : Parcelable
