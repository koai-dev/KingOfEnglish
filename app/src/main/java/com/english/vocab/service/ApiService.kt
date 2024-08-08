package com.english.vocab.service

import com.english.vocab.domain.models.Background
import com.english.vocab.domain.models.Question
import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import com.koai.base.network.BaseApiService
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService : BaseApiService {
    @GET("question")
    suspend fun getQuestion(
        @Query("level") level: Int,
    ): Response<Question>

    @POST("question/getRandom")
    suspend fun getQuestion(
        @Body levels: List<Int>,
    ): Response<Question>

    @POST("question/getHistory")
    suspend fun getHistory(
        @Body levels: List<Int>,
    ): Response<Question>

    @GET("user")
    suspend fun getUser(
        @Query("userId") userId: String,
    ): Response<User>

    @GET("topRanks")
    suspend fun getTopRanks(): Response<List<User>>

    @POST("user/add")
    suspend fun addUser(
        @Query("userId") userId: String,
        @Body user: User,
    ): Response<User>

    @POST("user/{userId}/update")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body user: User,
    ): Response<User>

    @GET("background/all")
    suspend fun getAllBackground(): Response<List<Background>>

    @GET("background")
    suspend fun getBackgroundById(
        @Query("id") id: Int,
    ): Response<Background>
}
