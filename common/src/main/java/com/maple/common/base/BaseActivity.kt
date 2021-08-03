package com.maple.common.base

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.gyf.immersionbar.ImmersionBar
import com.maple.baselib.utils.UIUtils
import com.maple.common.R
import com.maple.common.ext.toGone
import com.maple.common.ext.toVisible
import com.maple.common.utils.ToastUtils
import com.maple.common.widget.dialog.LoadingDialog
import com.maple.common.widget.state.showEmpty
import com.maple.common.widget.state.showError
import com.maple.common.widget.state.showLoading
import com.maple.common.widget.state.showSuccess
import com.zy.multistatepage.MultiStateContainer
import com.zy.multistatepage.bindMultiState
import kotlinx.android.synthetic.main.include_title.*
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
     * 多状态视图
     * 如果使用多状态视图,子类必须重写 hasUsedStateView 并返回 true,即可调用 onStateXXX() 等方法
     * 标题栏 不属于多状态视图内的View,布局文件中需要有一个id为 common_container 作为 切换的视图主体
     * 否则为整个 contentView
     */
    private val multiState by lazy {
        if(hasUsedStateView()){
            this.findViewById<View>(R.id.common_container)?.bindMultiState() ?: this.bindMultiState()
        }else{ null }
    }

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
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .navigationBarColor(R.color.common_white)
            .navigationBarDarkIcon(true)
            .init()
    }

    /***
     * 多状态布局 加载页面
     */
    open fun onStateLoading() {
        if(hasUsedStateView()) multiState?.showLoading()
    }

    /***
     * 多状态布局 空页面
     */
    open fun onStateEmpty() {
        if(hasUsedStateView()) multiState?.showEmpty()
    }

    /***
     * 多状态布局 错误页面
     */
    open fun onStateError() {
        if(hasUsedStateView()) multiState?.showError()
    }

    /***
     * 多状态布局 内容页面
     */
    open fun onStateSuccess() {
        if(hasUsedStateView()) multiState?.showSuccess()
    }

    /***
     * 多状态布局 错误页面的 重试
     */
    open fun onStateRetry(container: MultiStateContainer?) {}


    //-------------titleBar--------------------
    /**
     * 设置title
     * @param txt 标题
     */
    inline fun <reified T : BaseActivity> setTitle(txt: String): T {
        tv_title_center?.text = txt
        return this as T
    }

    /**
     * 设置左文本
     * @param txt 文本内容
     */
    inline fun <reified T : BaseActivity> setLeftTxt(txt: String): T {
        ll_title_left?.toVisible()
        tv_title_left?.apply {
            this.toVisible()
            this.text = txt
        }
        return this as T
    }

    /**
     * 设置右文本
     * @param txt 文本内容
     */
    inline fun <reified T : BaseActivity> setRightTxt(txt: String): T {
        ll_title_right?.toVisible()
        tv_title_right?.apply {
            this.toVisible()
            this.text = txt
        }
        return this as T
    }

    /**
     * 设置左按钮点击事件
     * @param m 点击事件
     */
    inline fun <reified T : BaseActivity> onBack(crossinline m: () -> Unit): T {
        ll_title_left?.toVisible()
        ibtn_title_left?.apply {
            this.toVisible()
            this.setOnClickListener {
                if (!UIUtils.isFastClick()) {
                    m()
                }
            }
        }
        return this as T
    }

    /**
     * 设置右按钮点击事件
     * @param m 点击事件
     */
    inline fun <reified T : BaseActivity> onSide(crossinline m: () -> Unit): T {
        ll_title_right?.toVisible()
        ibtn_title_right?.apply {
            this.toVisible()
            this.setOnClickListener {
                if (!UIUtils.isFastClick()) {
                    m()
                }
            }
        }
        return this as T
    }

    /**
     * 设置左文本点击事件
     * @param m 点击事件
     */
    inline fun <reified T : BaseActivity> onLeftText(crossinline m: () -> Unit): T {
        tv_title_left?.apply {
            this.setOnClickListener {
                if (!UIUtils.isFastClick()) {
                    m()
                }
            }
        }
        return this as T
    }

    /**
     * 设置右文本点击事件
     * @param m 点击事件
     */
    inline fun <reified T : BaseActivity> onRightText(crossinline m: () -> Unit): T {
        tv_title_right?.apply {
            this.setOnClickListener {
                if (!UIUtils.isFastClick()) {
                    m()
                }
            }
        }
        return this as T
    }

    /**
     * 是否显示左按钮
     * @param hasUse true 显示,false 隐藏
     * 默认显示
     */
    inline fun <reified T : BaseActivity> onUseBack(hasUse:Boolean): T {
        ibtn_title_left?.let {
            if(hasUse){
                it.toVisible()
            }else{
                it.toGone()
            }
        }
        return this as T
    }

    /**
     * 是否显示右按钮
     * @param hasUse true 显示,false 隐藏
     * 默认隐藏
     */
    inline fun <reified T : BaseActivity> onUseSide(hasUse:Boolean): T {
        ibtn_title_right?.let {
            if(hasUse){
                it.toVisible()
            }else{
                it.toGone()
            }
        }
        return this as T
    }

    /**
     * 设置 titleBar 背景颜色
     * @param resId 颜色
     */
    inline fun <reified T : BaseActivity> setTitleBarBackground(@ColorRes resId: Int): T {
        toolbar?.setBackgroundColor(UIUtils.getColor(resId))
        return this as T
    }

    /**
     * 设置 左按钮背景
     * @param resId 资源
     */
    inline fun <reified T : BaseActivity> setBackDrawable(@DrawableRes resId: Int): T {
        ibtn_title_left?.background = UIUtils.getDrawable(resId)
        return this as T
    }

    /**
     * 设置 右按钮背景
     * @param resId 资源
     */
    inline fun <reified T : BaseActivity> setSideDrawable(@DrawableRes resId: Int): T {
        ibtn_title_right?.background = UIUtils.getDrawable(resId)
        return this as T
    }

    //------------end------------------

}