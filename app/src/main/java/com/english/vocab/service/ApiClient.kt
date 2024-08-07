package com.english.vocab.service

import com.koai.base.network.BaseApiController
import com.english.vocab.utils.Constants

object ApiClient : BaseApiController<ApiService>() {
    override fun getApiService() = ApiService::class.java

    override fun getBaseUrl() = Constants.BASE_URL
}
