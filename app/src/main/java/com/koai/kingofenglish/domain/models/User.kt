package com.koai.kingofenglish.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class User(

	@field:SerializedName("currentLevel")
	val currentLevel: Int? = null,

	@field:SerializedName("timeActive")
	val timeActive: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("fcmToken")
	val fcmToken: String? = null,

	@field:SerializedName("points")
	val points: Int? = null
) : Parcelable
