package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Background
import com.koai.vocabvoyage.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface BackgroundUseCase {
    suspend fun getAll(): Flow<Response<List<Background>>>

    suspend fun getById(id: Int): Flow<Response<Background>>
}