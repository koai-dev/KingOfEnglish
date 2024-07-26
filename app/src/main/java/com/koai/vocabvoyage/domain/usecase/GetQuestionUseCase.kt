package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Question
import com.koai.vocabvoyage.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface GetQuestionUseCase {
    suspend fun execute(level: Int): Flow<Response<Question>>
}
