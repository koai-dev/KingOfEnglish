package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTopRankUseCaseImpl(private val service: ApiService) : GetTopRankUseCase {
    override suspend fun execute(): Flow<Response<List<User>>> =
        flow {
            emit(service.getTopRanks())
        }
}
