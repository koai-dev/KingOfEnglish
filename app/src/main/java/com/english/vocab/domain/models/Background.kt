package com.english.vocab.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Background(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("link") val link: String,
) : Parcelable
