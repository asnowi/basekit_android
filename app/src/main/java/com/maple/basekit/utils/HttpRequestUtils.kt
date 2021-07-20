package com.maple.basekit.utils

import java.util.*

class HttpRequestUtils {

    companion object{

        fun requestHeader(): WeakHashMap<String, Any> {
            val header: WeakHashMap<String, Any> = WeakHashMap()
            header.put("header", "header")
            return header
        }

        fun requestParams(): WeakHashMap<String, Any> {
            val params: WeakHashMap<String, Any> = WeakHashMap()
            params.put("params", "params")
            return params
        }
    }
}