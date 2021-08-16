package com.maple.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
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
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
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

    /// 下拉刷新
    protected var refreshLayout: SmartRefreshLayout? = null
    protected var recyclerView: RecyclerView? = null

    ///是否启用下拉刷新功能 默认不启用
    open fun isEnableRefresh(): Boolean = false
    ///是否启用上拉加载功能 默认不启用
    open fun isEnableLoadMore(): Boolean = false

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

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        refreshLayout = this.findViewById(R.id.common_refreshLayout)
        recyclerView = this.findViewById(R.id.common_recyclerView)

        if(isEnableRefresh()) {
            refreshLayout?.setEnableRefresh(isEnableRefresh())//是否启用下拉刷新功能
        }
        if(isEnableLoadMore()) {
            refreshLayout?.setEnableLoadMore(isEnableLoadMore())//是否启用上拉加载功能
        }
        if(isEnableRefresh()) {
            refreshLayout?.setOnRefreshListener { ref ->
                onRefreshData()
            }
        }
        if(isEnableLoadMore()) {
            refreshLayout?.setOnLoadMoreListener { ref ->
                onLoadMoreData()
            }
        }
    }

    /// 下拉刷新数据
    open fun onRefreshData() {}

    /// 上拉加载数据
    open fun onLoadMoreData() {}

    //结束下拉刷新
    protected fun finishRefresh() {
        refreshLayout?.let {
            if (it.isRefreshing) it.finishRefresh(300)
        }
    }

    //结束加载更多
    protected fun finishLoadMore() {
        refreshLayout?.let {
            if (it.isLoading) it.finishLoadMore(300)
        }
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
        UltimateBarX.with(this)
            .color(color)
            .light(true)
            .applyStatusBar()

//        val background = BarBackground.newInstance()    // 创建 background 对象
//           // .color(color)                   // 状态栏/导航栏背景颜色（色值）
//            .colorRes(color)              // 状态栏/导航栏背景颜色（资源id）
//          //  .drawableRes(R.drawable.bg_common)          // 状态栏/导航栏背景 drawable
//        // 设置背景的方法三选一即可
//
//        val config = BarConfig.newInstance()            // 创建配置对象
//            .fitWindow(true)                 // 布局是否侵入状态栏（true 不侵入，false 侵入）
//            .background(background)                     // 设置 background 对象
//            .light(false)
//
//        UltimateBarX.with(this)                         // 对当前 Activity 或 Fragment 生效
//            .config(config)                             // 使用配置
//            .applyStatusBar()                           // 应用到状态栏
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
        if(hasUsedStateView())multiState?.let { c ->
            c.showError(callBack = { it.retry = {
                onStateRetry(c)
            } })
        }
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