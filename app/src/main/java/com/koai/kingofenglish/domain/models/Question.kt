package com.koai.kingofenglish.domain.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Question(

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("thumb")
	val thumb: String? = null,

	@field:SerializedName("levelQuestion")
	val levelQuestion: Int? = null,

	@field:SerializedName("answers")
	val answers: ArrayList<String?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("answerRight")
	val answerRight: String? = null,

	@field:SerializedName("subQuestion")
	val subQuestion: String? = null,


) : Parcelable
