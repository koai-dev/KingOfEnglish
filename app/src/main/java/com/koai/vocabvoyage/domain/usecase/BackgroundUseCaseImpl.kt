package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Background
import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BackgroundUseCaseImpl(private val service: ApiService) : BackgroundUseCase {
    override suspend fun getAll(): Flow<Response<List<Background>>> = flow {
        emit(service.getAllBackground())
    }

    override suspend fun getById(id: Int): Flow<Response<Background>> = flow {
        emit(service.getBackgroundById(id))
    }
}