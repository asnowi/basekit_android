package com.maple.common.utils

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.maple.common.app.CommonApp
import com.maple.common.widget.snackbar.AnimatedSnackbar

class ToastUtils {

    companion object {
        @JvmStatic
        fun showToast(s: String?) {
            ToastUtils.showShort(s)
        }

        @JvmStatic
        fun showSnackbar (context: Context?, s: String?) {
            if(null != context && null != s){
                AnimatedSnackbar(context)
                    .setMessage(s)
                    .show()
            }
        }
    }
}