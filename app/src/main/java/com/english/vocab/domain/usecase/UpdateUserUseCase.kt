package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UpdateUserUseCase {
    suspend fun execute(user: User): Flow<Response<User>>
}
