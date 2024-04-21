package com.koai.kingofenglish.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Background(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("link") val link: String
) : Parcelable