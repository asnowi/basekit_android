package com.maple.basekit.model.repository

import com.maple.basekit.utils.HttpParamsUtils
import com.maple.baselib.base.BaseRepository
import java.util.*

class UserRepository: BaseRepository(){

    override fun getPublicParams(): WeakHashMap<String, Any> {
        return HttpParamsUtils.requestParams()
    }
}