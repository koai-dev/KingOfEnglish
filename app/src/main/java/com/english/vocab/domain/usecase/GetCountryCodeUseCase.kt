package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.CountryModel

interface GetCountryCodeUseCase {
    suspend fun execute(): CountryModel
}
