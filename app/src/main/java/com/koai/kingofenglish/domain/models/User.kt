package com.koai.kingofenglish.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class User(
    @field:SerializedName("currentLevel")
    var currentLevel: Int? = null,
    @field:SerializedName("timeActive")
    val timeActive: Long? = null,
    @field:SerializedName("name")
    var name: String? = null,
    @field:SerializedName("avatar")
    var avatar: String? = null,
    @field:SerializedName("accessToken")
    val accessToken: String? = null,
    @field:SerializedName("userId")
    val userId: String? = null,
    @field:SerializedName("fcmToken")
    var fcmToken: String? = null,
    @field:SerializedName("points")
    var points: Int? = null,
    @field: SerializedName("top")
    var top: Int? = null
) : Parcelable
