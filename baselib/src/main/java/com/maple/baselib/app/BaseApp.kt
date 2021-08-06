package com.maple.baselib.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.maple.baselib.app.manager.ForebackLifeObserver
import com.maple.baselib.utils.LogUtils

abstract class BaseApp: Application(), ViewModelStoreOwner {

    abstract fun getAppPackage(): String

    abstract fun initSDK(app: Application)

    companion object {
        @JvmStatic
        lateinit var instance: BaseApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initApp()
        initSDK(this)
    }

    open fun initApp() {
        Utils.init(this)
        SPUtils.getInstance(this.getAppPackage())
        LogUtils.logGGQ(this.getAppPackage())
        registerLifecycle()
    }

    private fun registerLifecycle() {
        // 监听所有 Activity 的创建和销毁
        ProcessLifecycleOwner.get().lifecycle.addObserver(ForebackLifeObserver())
    }
}