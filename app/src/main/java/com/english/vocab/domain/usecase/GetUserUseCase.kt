package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {
    suspend fun execute(userId: String): Flow<Response<User>>
}
