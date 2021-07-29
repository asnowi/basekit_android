package com.maple.common.base

import com.maple.common.utils.ToastUtils
import com.maple.common.widget.dialog.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.maple.baselib.base.BaseFragment as B

abstract class BaseFragment: B(){


    /**
     * toast
     * @param s 显示内容
     */
    open fun showToast(s: String?) {
//        ToastUtils.showToast(s)
        ToastUtils.showSnackbar(context,s)
    }

    private var loadingDialog: LoadingDialog? = null


    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = context?.let {
                LoadingDialog(it)
            }
        }
        loadingDialog?.run {
            if(!this.isShowing) this.show()
        }
    }

    open fun dismissLoading() {
        loadingDialog?.run {
            if (isShowing) {
                runBlocking {
                    delay(500L)
                }
                dismiss()
            }
        }
    }
}