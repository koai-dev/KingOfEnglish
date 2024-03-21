package com.koai.kingofenglish.domain.usecase

import com.koai.base.network.ResponseStatus
import kotlinx.coroutines.flow.Flow

interface GetQuestionUseCase {
    fun execute(level: Int): Flow<ResponseStatus<Any>>
}