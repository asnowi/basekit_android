package com.maple.basekit.vm

import androidx.databinding.ObservableField
import com.maple.basekit.db.DBHelper
import com.maple.basekit.db.UserInfo
import com.maple.baselib.app.manager.SingleLiveEvent
import com.maple.baselib.base.BaseViewModel

class HomeViewModel: BaseViewModel() {

    val userInfoEvent: SingleLiveEvent<UserInfo?> = SingleLiveEvent()

    val noticeEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val loadingEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val logoutEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val avatarEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    init {
        setUserInfo()
    }

    fun setUserInfo () {
        userInfoEvent.postValue(DBHelper.getUserInfo())
    }

    fun onTest1Click() {
        noticeEvent.call()
    }

    fun onTest2Click() {
        defUI.onToast("测试2")
    }

    fun onTest3Click () {
        loadingEvent.call()
    }

    fun onAvatar () {
        avatarEvent.call()
    }

    fun onLogout () {
        logoutEvent.call()
    }
}