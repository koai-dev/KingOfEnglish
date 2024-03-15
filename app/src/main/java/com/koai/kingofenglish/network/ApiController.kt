package com.koai.kingofenglish.network

import com.koai.base.network.BaseApiController

class ApiController : BaseApiController<ApiService>() {
    private val baseUrl ="https://king-of-english-d24f79b832c8.herokuapp.com/api/"
    override fun getApiService(): Class<ApiService> {
        return ApiService::class.java
    }

    override fun getBaseUrl(): String {
        return baseUrl
    }
}