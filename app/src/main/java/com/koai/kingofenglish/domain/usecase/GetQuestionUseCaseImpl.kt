package com.koai.kingofenglish.domain.usecase

import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetQuestionUseCaseImpl(private val service: ApiService) : GetQuestionUseCase {
    override fun execute(level: Int): Flow<ResponseStatus<Any>> = flow {
        service.getQuestion(level).onStart {
            emit(ResponseStatus.Loading)
        }.catch {
            emit(ResponseStatus.Error(message = it.message.toString()))
        }.collect{
            emit(it)
        }
    }
}