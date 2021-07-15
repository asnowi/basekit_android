package com.maple.basekit.model

import com.maple.basekit.app.config.Config
import com.maple.basekit.model.api.ApiService
import com.maple.basekit.model.handler.URLInterceptor
import com.maple.baselib.http.BaseRetrofitClient
import okhttp3.OkHttpClient

object RetrofitClient: BaseRetrofitClient() {

    val service by lazy { getService(ApiService::class.java, Config.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(URLInterceptor())
    }
}