package com.maple.baselib.http.resp

import com.maple.baselib.http.error.ERROR

/**
 * 响应数据封装
 */
open class BaseResp<out T> {
    var code:String = ERROR.UNKNOWN.getKey()
    var msg:String = ERROR.UNKNOWN.getValue()
    val data:T? = null
}