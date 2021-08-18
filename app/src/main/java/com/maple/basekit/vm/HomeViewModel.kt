package com.maple.basekit.vm

import androidx.databinding.ObservableField
import com.maple.basekit.db.DBHelper
import com.maple.basekit.db.UserInfo
import com.maple.baselib.app.manager.SingleLiveEvent
import com.maple.baselib.base.BaseViewModel
import com.maple.baselib.utils.LogUtils

class HomeViewModel: BaseViewModel() {
    val userInfo: ObservableField<UserInfo?> = ObservableField()

    val noticeEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val logoutEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val avatarEvent: SingleLiveEvent<Any> = SingleLiveEvent()


    init {
        userInfo.set(DBHelper.getUserInfo())
        LogUtils.logGGQ("==UserInfo===>>>${userInfo.get() != null}")
    }

    fun onNotice () {
        noticeEvent.call()
    }
    fun onAvatar () {
        avatarEvent.call()
    }

    fun onLogout () {
        logoutEvent.call()
    }
}