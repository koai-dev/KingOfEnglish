package com.koai.kingofenglish.network

import com.koai.base.network.BaseApiService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService : BaseApiService {
    @GET("question")
    fun getQuestion(@Query("level") level: Int): Call<DataApi>
}
