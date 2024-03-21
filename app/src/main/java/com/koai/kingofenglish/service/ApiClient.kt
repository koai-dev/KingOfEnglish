package com.koai.kingofenglish.service

import com.koai.base.network.BaseApiController
import com.koai.kingofenglish.utils.Constants

object ApiClient : BaseApiController<ApiService>() {
    override fun getApiService() = ApiService::class.java

    override fun getBaseUrl() = Constants.BASE_URL
}