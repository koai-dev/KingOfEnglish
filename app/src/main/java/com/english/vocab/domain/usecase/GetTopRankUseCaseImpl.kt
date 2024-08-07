package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Response
import com.english.vocab.domain.models.User
import com.english.vocab.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopRankUseCaseImpl(private val service: ApiService) : GetTopRankUseCase {
    override suspend fun execute(): Flow<Response<List<User>>> =
        flow {
            emit(service.getTopRanks())
        }
}
