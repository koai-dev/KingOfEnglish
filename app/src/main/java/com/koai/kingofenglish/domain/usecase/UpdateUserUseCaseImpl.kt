package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateUserUseCaseImpl(private val service: ApiService): UpdateUserUseCase {
    override suspend fun execute(user: User): Flow<Response<User>> = flow {
        user.userId?.let {
            emit(service.updateUser(userId = user.userId, user = user))
        }
    }
}