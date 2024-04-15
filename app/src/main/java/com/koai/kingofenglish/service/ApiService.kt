package com.koai.kingofenglish.service

import com.koai.base.network.BaseApiService
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.Question
import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService : BaseApiService {
    @GET("question")
    suspend fun getQuestion(@Query("level") level: Int): Response<Question>

    @GET("user")
    suspend fun getUser(@Query("userId") userId: String): ResponseStatus<Response<User>>


}