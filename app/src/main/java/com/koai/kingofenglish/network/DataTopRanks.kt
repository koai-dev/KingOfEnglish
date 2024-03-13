package com.koai.kingofenglish.network


import com.google.gson.annotations.SerializedName

data class DataTopRanks(
    @SerializedName("data")
    var `data`: List<Data?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("statusCode")
    var statusCode: Int?
) {
    data class Data(
        @SerializedName("accessToken")
        var accessToken: String?,
        @SerializedName("avatar")
        var avatar: String?,
        @SerializedName("currentLevel")
        var currentLevel: Int?,
        @SerializedName("fcmToken")
        var fcmToken: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("points")
        var points: Int?,
        @SerializedName("timeActive")
        var timeActive: Int?,
        @SerializedName("userId")
        var userId: String?
    )
}