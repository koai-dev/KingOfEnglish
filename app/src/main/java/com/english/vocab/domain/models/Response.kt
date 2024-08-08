package com.english.vocab.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Keep
@Parcelize
data class Response<T>(
    @field:SerializedName("data")
    val data: @RawValue T? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("statusCode")
    val statusCode: Int? = null,
) : Parcelable
