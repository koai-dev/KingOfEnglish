package com.koai.kingofenglish.service

import com.koai.base.network.BaseApiService
import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.Question
import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService : BaseApiService {
    @GET("question")
    suspend fun getQuestion(@Query("level") level: Int): Response<Question>

    @GET("user")
    suspend fun getUser(@Query("userId") userId: String): Response<User>

    @GET("topRanks")
    suspend fun getTopRanks(): Response<List<User>>

    @POST("user/add")
    suspend fun addUser(@Query("userId") userId: String, @Body user: User): Response<User>

    @POST("user/{userId}/update")
    suspend fun updateUser(@Path("userId") userId: String, @Body user: User): Response<User>
}