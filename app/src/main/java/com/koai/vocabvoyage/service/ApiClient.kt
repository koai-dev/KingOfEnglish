package com.koai.vocabvoyage.service

import com.koai.base.network.BaseApiController
import com.koai.vocabvoyage.utils.Constants

object ApiClient : BaseApiController<ApiService>() {
    override fun getApiService() = ApiService::class.java

    override fun getBaseUrl() = Constants.BASE_URL
}
