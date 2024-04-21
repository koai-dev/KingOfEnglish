package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AddUserUseCaseImpl(private val service: ApiService) : AddUserUseCase {
    override suspend fun execute(user: User): Flow<Response<User>> =
        flow {
            user.userId?.let {
                emit(service.addUser(userId = user.userId, user = user))
            }
        }
}
