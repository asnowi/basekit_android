package com.maple.basekit.db

import android.text.TextUtils
import com.google.gson.Gson
import com.tencent.mmkv.MMKV

class DBHelper private constructor() {


    companion object {
        private const val KEY_USER: String = "USERINFO"

        private val kv: MMKV? by lazy { MMKV.defaultMMKV() }

        fun saveUser(user: UserInfo?): Boolean {
            if (null != user) {
                return kv?.encode(KEY_USER, user.toString())?: false
            }
            return false
        }

        fun getUserInfo (): UserInfo? {
            val userInfo: String? =  kv?.getString(KEY_USER,"")
            if(!TextUtils.isEmpty(userInfo)) {
                try {
                   return Gson().fromJson<UserInfo>(userInfo, UserInfo::class.java)
                }catch (e: Exception){
                    e.fillInStackTrace()
                }
            }
            return null
        }

        fun removeUser() {
            kv?.remove(KEY_USER)
        }
    }


}