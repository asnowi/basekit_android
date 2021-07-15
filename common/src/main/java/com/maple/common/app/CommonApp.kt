package com.maple.common.app

import com.maple.baselib.app.BaseApp

abstract class CommonApp: BaseApp() {

    companion object {
        @JvmStatic
        lateinit var instance: CommonApp
            private set
    }


    override fun initApp() {
        super.initApp()

    }

}