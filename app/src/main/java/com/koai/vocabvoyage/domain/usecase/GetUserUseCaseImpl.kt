package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.models.User
import com.koai.vocabvoyage.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCaseImpl(private val service: ApiService) : GetUserUseCase {
    override suspend fun execute(userId: String): Flow<Response<User>> =
        flow {
            emit(service.getUser(userId))
        }
}
