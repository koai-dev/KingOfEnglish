package com.koai.kingofenglish.service

import com.koai.base.network.BaseApiService
import com.koai.base.network.ResponseStatus
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService : BaseApiService {
    @GET("question")
    suspend fun getQuestion(@Query("level") level: Int): Flow<ResponseStatus<Any>>
}