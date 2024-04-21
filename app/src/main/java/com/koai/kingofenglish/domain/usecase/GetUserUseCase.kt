package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {
    suspend fun execute(userId: String): Flow<Response<User>>
}
