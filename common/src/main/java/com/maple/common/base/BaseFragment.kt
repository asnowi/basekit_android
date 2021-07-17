package com.maple.common.base

import com.maple.common.utils.ToastUtils
import com.maple.baselib.base.BaseFragment as B

abstract class BaseFragment: B(){


    /**
     * toast
     * @param s 显示内容
     */
    open fun showToast(s: String?) {
        ToastUtils.showToast(s)
    }
}