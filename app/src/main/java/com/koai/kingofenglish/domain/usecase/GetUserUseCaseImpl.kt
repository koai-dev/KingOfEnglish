package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCaseImpl(private val service: ApiService) : GetUserUseCase {
    override suspend fun execute(userId: String): Flow<Response<User>> =
        flow {
            emit(service.getUser(userId))
        }
}
