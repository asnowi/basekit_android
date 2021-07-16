package com.maple.common.utils

import com.blankj.utilcode.util.ToastUtils

class ToastUtils {

    companion object {
        @JvmStatic
        fun showToast(s: String?) {
            ToastUtils.showShort(s)
        }
    }
}