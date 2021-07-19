package com.maple.baselib.app

object ResultCode {
    fun isSuccess (code: String): Boolean{
        return code == "200"
    }
}