package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AddUserUseCase {
    suspend fun execute(user: User): Flow<Response<User>>
}
