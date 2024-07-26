package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AddUserUseCase {
    suspend fun execute(user: User): Flow<Response<User>>
}
