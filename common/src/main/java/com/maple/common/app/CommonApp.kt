package com.maple.common.app

import com.maple.baselib.app.BaseApp
import com.maple.baselib.utils.UIUtils
import com.maple.common.R
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV

abstract class CommonApp: BaseApp() {


    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.common_white, R.color.common_refresh)
            ClassicsHeader(context).setDrawableArrowSize(14f).setDrawableProgressSize(14f).setTextSizeTitle(14f).setTextSizeTime(10f)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(context).setDrawableSize(14f).setTextSizeTitle(14f).setAccentColor(
            UIUtils.getColor(R.color.common_refresh)) }
    }


    companion object {
        @JvmStatic
        lateinit var instance: CommonApp
            private set
    }


    override fun initApp() {
        super.initApp()
        instance = this
        MMKV.initialize(this)
    }

}