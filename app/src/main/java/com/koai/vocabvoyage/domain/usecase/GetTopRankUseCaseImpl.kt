package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.models.User
import com.koai.vocabvoyage.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopRankUseCaseImpl(private val service: ApiService) : GetTopRankUseCase {
    override suspend fun execute(): Flow<Response<List<User>>> =
        flow {
            emit(service.getTopRanks())
        }
}
