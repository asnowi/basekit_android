package com.maple.common.base

import com.gyf.immersionbar.ktx.immersionBar
import com.maple.common.utils.ToastUtils
import com.maple.common.widget.dialog.LoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.maple.baselib.base.BaseActivity as B

/***
 * 上层基础组件封装
 * 用于无网络请求等简单页面
 */
abstract class BaseActivity: B(){

    /**
     * 是否使用多状态视图
     */
    open fun hasUsedStateView(): Boolean = false

    /**
     * toast
     * @param s 显示内容
     */
    open fun showToast(s: String?) {
//        ToastUtils.showToast(s)
        ToastUtils.showSnackbar(this, s)
    }


    private var loadingDialog: LoadingDialog? = null

    /// 展示 loading
    open fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        loadingDialog?.run {
            if(!this.isShowing)  this.show()
        }
    }

    /// 关闭 loading
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

    override fun setStatusBarMode(color: Int) {
        super.setStatusBarMode(color)
    }

}