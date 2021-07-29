package com.maple.common.app

import com.maple.baselib.app.BaseApp
import com.tencent.mmkv.MMKV

abstract class CommonApp: BaseApp() {

    companion object {
        @JvmStatic
        lateinit var instance: CommonApp
            private set
    }


    override fun initApp() {
        super.initApp()
        MMKV.initialize(this)
    }

}