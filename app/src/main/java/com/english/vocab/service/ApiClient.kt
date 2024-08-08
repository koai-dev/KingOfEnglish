package com.english.vocab.service

import com.english.vocab.utils.Constants
import com.koai.base.network.BaseApiController

object ApiClient : BaseApiController<ApiService>() {
    override fun getApiService() = ApiService::class.java

    override fun getBaseUrl() = Constants.BASE_URL
}
