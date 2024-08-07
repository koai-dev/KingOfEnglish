package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Question
import com.english.vocab.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface GetQuestionUseCase {
    suspend fun execute(level: Int): Flow<Response<Question>>
}
