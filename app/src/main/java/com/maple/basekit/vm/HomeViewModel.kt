package com.maple.basekit.vm

import com.maple.baselib.app.manager.SingleLiveEvent
import com.maple.baselib.base.BaseViewModel

class HomeViewModel: BaseViewModel() {

    val noticeEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    fun onTest1Click() {
        noticeEvent.call()
    }

    fun onTest2Click() {
        defUI.onToast("测试2")
    }
}