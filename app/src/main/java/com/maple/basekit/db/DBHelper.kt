package com.maple.basekit.db

import android.text.TextUtils
import com.google.gson.Gson
import com.maple.baselib.utils.LogUtils
import com.tencent.mmkv.MMKV

class DBHelper private constructor() {


    companion object {
        private const val KEY_USER: String = "USERINFO"

        private val kv: MMKV? by lazy { MMKV.defaultMMKV() }
        private val gson: Gson? by lazy { Gson() }

        fun saveUser(user: UserInfo?): Boolean {
            if (null != user) {
                try {
                    return kv?.encode(KEY_USER, gson?.toJson(user))?: false
                } catch (e: Exception) {
                    e.fillInStackTrace()
                }
            }
            return false
        }

        fun getUserInfo (): UserInfo? {
            val userInfo: String? =  kv?.getString(KEY_USER,"")
            if(!TextUtils.isEmpty(userInfo)) {
                try {
                   return gson?.fromJson<UserInfo>(userInfo, UserInfo::class.java)
                }catch (e: Exception){
                    e.fillInStackTrace()
                    LogUtils.logGGQ("--异常-->${e.fillInStackTrace()}")
                }
            }
            return null
        }

        fun removeUser() {
            kv?.remove(KEY_USER)
        }

        fun updateUserInfo(userInfo: UserInfo?): Boolean {
            return saveUser(userInfo)
        }
    }

}