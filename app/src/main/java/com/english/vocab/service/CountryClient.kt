package com.english.vocab.service

import com.koai.base.network.BaseApiController

object CountryClient : BaseApiController<CountryService>() {
    override fun getBaseUrl(): String = "http://ip-api.com/"

    override fun getApiService() = CountryService::class.java
}
