package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.models.User
import kotlinx.coroutines.flow.Flow

interface GetUserUseCase {
    suspend fun execute(userId: String): Flow<Response<User>>
}
