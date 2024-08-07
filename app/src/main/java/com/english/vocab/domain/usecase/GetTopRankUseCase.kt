package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetTopRankUseCase {
    suspend fun execute(): Flow<Response<List<User>>>
}
