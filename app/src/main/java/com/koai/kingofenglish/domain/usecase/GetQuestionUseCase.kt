package com.koai.kingofenglish.domain.usecase

import com.koai.base.network.ResponseStatus
import com.koai.kingofenglish.domain.models.Question
import kotlinx.coroutines.flow.Flow

interface GetQuestionUseCase {
    fun execute(level: Int): Flow<ResponseStatus<Question>>
}