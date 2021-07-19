package com.maple.basekit.model.api

import com.maple.basekit.model.entity.LoginEntity
import com.maple.baselib.http.resp.BaseResp
import com.maple.common.app.Global
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("${Global.DOMAIN}:${Global.URL_HYNTECH}")
    @POST(ApiURL.URL_USER_LOGIN)
    suspend fun loginPhone(@Body requestBody: RequestBody): BaseResp<LoginEntity>

}