package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import com.english.vocab.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCaseImpl(private val service: ApiService) : GetUserUseCase {
    override suspend fun execute(userId: String): Flow<Response<User>> =
        flow {
            emit(service.getUser(userId))
        }
}
