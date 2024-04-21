package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetTopRankUseCase {
    suspend fun execute(): Flow<Response<List<User>>>
}
