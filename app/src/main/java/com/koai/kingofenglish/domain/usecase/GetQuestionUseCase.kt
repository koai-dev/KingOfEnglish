package com.koai.kingofenglish.domain.usecase

import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.Question
import com.koai.kingofenglish.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface GetQuestionUseCase {
    suspend fun execute(level: Int): Flow<Response<Question>>
}