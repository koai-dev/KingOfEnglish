package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetTopRankUseCase {
    suspend fun execute(): Flow<Response<List<User>>>
}
