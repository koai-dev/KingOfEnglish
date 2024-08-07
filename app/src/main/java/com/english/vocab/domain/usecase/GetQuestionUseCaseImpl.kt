package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.Question
import com.english.vocab.domain.models.Response
import com.english.vocab.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuestionUseCaseImpl(private val service: ApiService) : GetQuestionUseCase {
    override suspend fun execute(level: Int): Flow<Response<Question>> =
        flow {
            emit(service.getQuestion(level))
        }
}
