package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.models.User
import com.koai.vocabvoyage.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateUserUseCaseImpl(private val service: ApiService) : UpdateUserUseCase {
    override suspend fun execute(user: User): Flow<Response<User>> =
        flow {
            user.userId?.let {
                emit(service.updateUser(userId = user.userId, user = user))
            }
        }
}
