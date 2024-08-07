package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Background
import com.english.vocab.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface BackgroundUseCase {
    suspend fun getAll(): Flow<Response<List<Background>>>

    suspend fun getById(id: Int): Flow<Response<Background>>
}