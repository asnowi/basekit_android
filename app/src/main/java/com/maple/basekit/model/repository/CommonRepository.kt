package com.maple.basekit.model.repository

import com.maple.basekit.model.RetrofitClient
import com.maple.basekit.utils.HttpRequestUtils
import com.maple.baselib.base.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class CommonRepository: BaseRepository(){

    private val retrofitClient = RetrofitClient.service


    override fun getPublicParams(): WeakHashMap<String, Any> {
        return HttpRequestUtils.requestParams()
    }

    suspend fun loginPhone(params: WeakHashMap<String, Any>) = withContext(Dispatchers.IO) {
        retrofitClient.loginPhone(params2Body(params))
    }
}