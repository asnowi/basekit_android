package com.maple.basekit.app

import android.app.Application
import com.maple.common.app.CommonApp

class MyApplication: CommonApp() {

    companion object {
        @JvmStatic
        lateinit var instance: MyApplication
            private set
    }

    override fun initSDK(app: Application) {

    }
}