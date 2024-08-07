package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Background
import com.english.vocab.domain.models.Response
import com.english.vocab.service.ApiService
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