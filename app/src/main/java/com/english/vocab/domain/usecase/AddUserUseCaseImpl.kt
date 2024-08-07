package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import com.english.vocab.service.ApiService
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
