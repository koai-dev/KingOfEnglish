package com.koai.kingofenglish.network


import com.google.gson.annotations.SerializedName

data class DataApi(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("statusCode")
    val statusCode: Int? = null
) {
    data class Data(
        @SerializedName("answerRight")
        val answerRight: String? = null,
        @SerializedName("answers")
        val answers: List<String?>? = null,
        @SerializedName("level")
        val level: Int? = null,
        @SerializedName("levelQuestion")
        val levelQuestion: Int? = null,
        @SerializedName("question")
        val question: String? = null,
        @SerializedName("subQuestion")
        val subQuestion: String? = null,
        @SerializedName("thumb")
        val thumb: String? = null,
        @SerializedName("type")
        val type: String? = null
    )
}