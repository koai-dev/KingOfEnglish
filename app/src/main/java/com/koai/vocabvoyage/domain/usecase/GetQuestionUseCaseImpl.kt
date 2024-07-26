package com.koai.vocabvoyage.domain.usecase

import com.koai.vocabvoyage.domain.models.Question
import com.koai.vocabvoyage.domain.models.Response
import com.koai.vocabvoyage.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuestionUseCaseImpl(private val service: ApiService) : GetQuestionUseCase {
    override suspend fun execute(level: Int): Flow<Response<Question>> =
        flow {
            emit(service.getQuestion(level))
        }
}
