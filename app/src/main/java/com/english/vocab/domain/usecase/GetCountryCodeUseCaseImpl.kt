package com.english.vocab.domain.usecase

import com.english.vocab.domain.models.CountryModel
import com.english.vocab.service.CountryService

class GetCountryCodeUseCaseImpl(private val service: CountryService) : GetCountryCodeUseCase {
    override suspend fun execute(): CountryModel {
        return service.execute()
    }
}
