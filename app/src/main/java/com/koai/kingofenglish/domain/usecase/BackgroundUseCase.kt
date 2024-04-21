package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Background
import com.koai.kingofenglish.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface BackgroundUseCase {
    suspend fun getAll(): Flow<Response<List<Background>>>

    suspend fun getById(id: Int): Flow<Response<Background>>
}