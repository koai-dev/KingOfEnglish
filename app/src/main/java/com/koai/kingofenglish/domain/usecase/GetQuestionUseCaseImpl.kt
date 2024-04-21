package com.koai.kingofenglish.domain.usecase

import com.koai.kingofenglish.domain.models.Question
import com.koai.kingofenglish.domain.models.Response
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetQuestionUseCaseImpl(private val service: ApiService) : GetQuestionUseCase {
    override suspend fun execute(level: Int): Flow<Response<Question>> =
        flow {
            emit(service.getQuestion(level))
        }
}
