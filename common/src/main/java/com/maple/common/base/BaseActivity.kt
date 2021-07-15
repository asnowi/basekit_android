package com.maple.common.base

import com.maple.common.utils.ToastUtils
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
        ToastUtils.showToast(s)
    }
}