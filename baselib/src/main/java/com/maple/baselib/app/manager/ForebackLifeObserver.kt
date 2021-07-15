package com.maple.baselib.app.manager

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.maple.baselib.utils.LogUtils

/**
 * 前后台切换的观察者
 */
class ForebackLifeObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        LogUtils.logGGQ("APP进入前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        LogUtils.logGGQ("APP进入后台")
    }
}