package com.english.vocab.service

import com.english.vocab.domain.models.CountryModel
import retrofit2.http.GET

interface CountryService {
    @GET("json")
    suspend fun execute(): CountryModel
}