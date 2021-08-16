package com.maple.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.maple.baselib.utils.UIUtils
import com.maple.common.R
import com.maple.common.ext.toGone
import com.maple.common.ext.toVisible
import com.maple.common.utils.ToastUtils
import com.maple.common.widget.dialog.LoadingDialog
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
import com.zackratos.ultimatebarx.ultimatebarx.bean.BarBackground
import com.zackratos.ultimatebarx.ultimatebarx.bean.BarConfig
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.textColor
import com.maple.baselib.base.BaseFragment as B


abstract class BaseFragment: B() {

    protected var refreshLayout: SmartRefreshLayout? = null
    protected var recyclerView: RecyclerView? = null

    open fun isEnableRefresh(): Boolean = false
    open fun isEnableLoadMore(): Boolean = false

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        refreshLayout = view.findViewById(R.id.common_refreshLayout)
        recyclerView = view.findViewById(R.id.common_recyclerView)

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

    open fun onRefreshData() {}

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

    //-------------titleBar--------------------

    inline fun <reified T : BaseFragment> setTitle(txt: String): T {
        tv_title_center?.text = txt
        return this as T
    }

    inline fun <reified T : BaseFragment> setLeftTxt(txt: String): T {
        ll_title_left?.toVisible()
        tv_title_left?.apply {
            this.toVisible()
            this.text = txt
        }
        return this as T
    }

    inline fun <reified T : BaseFragment> setRightTxt(txt: String): T {
        ll_title_right?.toVisible()
        tv_title_right?.apply {
            this.toVisible()
            this.text = txt
        }
        return this as T
    }

    inline fun <reified T : BaseFragment> onBack(crossinline m: () -> Unit): T {
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

    inline fun <reified T : BaseFragment> onSide(crossinline m: () -> Unit): T {
        ll_title_right.toVisible()
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

    inline fun <reified T : BaseFragment> onLeftText(crossinline m: () -> Unit): T {
        tv_title_left?.apply {
            this.setOnClickListener {
                if (!UIUtils.isFastClick()) {
                    m()
                }
            }
        }
        return this as T
    }

    inline fun <reified T : BaseFragment> onRightText(crossinline m: () -> Unit): T {
        tv_title_right?.apply {
            this.setOnClickListener {
                if (!UIUtils.isFastClick()) {
                    m()
                }
            }
        }
        return this as T
    }

    inline fun <reified T : BaseFragment> onUseBack(hasUse:Boolean): T {
        ibtn_title_left?.let {
            if(hasUse){
                it.toVisible()
            }else{
                it.toGone()
            }
        }
        return this as T
    }

    inline fun <reified T : BaseFragment> onUseSide(hasUse:Boolean): T {
        ibtn_title_right?.let {
            if(hasUse){
                it.toVisible()
            }else{
                it.toGone()
            }
        }
        return this as T
    }

    inline fun <reified T : BaseFragment> setTitleBarBackground(@ColorRes color: Int): T {
        toolbar?.setBackgroundColor(UIUtils.getColor(color))
        return this as T
    }

    inline fun <reified T : BaseFragment> setBackDrawable(@DrawableRes resId: Int): T {
        ibtn_title_left?.background = UIUtils.getDrawable(resId)
        return this as T
    }

    inline fun <reified T : BaseFragment> setSideDrawable(@DrawableRes resId: Int): T {
        ibtn_title_right?.background = UIUtils.getDrawable(resId)
        return this as T
    }


    inline fun <reified T : BaseFragment> setTxtColor(@ColorRes color: Int): T {
        tv_title_center?.setTextColor(UIUtils.getColor(color))
        tv_title_left?.let {
            if(it.isVisible) it.textColor = UIUtils.getColor(color)
        }
        tv_title_right?.let {
            if(it.isVisible) it.textColor = UIUtils.getColor(color)
        }
        return this as T
    }
    //------------end------------------

    override fun setStatusBarMode(color: Int) {
        super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .color(color)
            .light(true)
            .applyStatusBar()


//        val background = BarBackground.newInstance()    // 创建 background 对象
//            // .color(color)                            // 状态栏/导航栏背景颜色（色值）
//            .colorRes(color)                            // 状态栏/导航栏背景颜色（资源id）
//        //  .drawableRes(R.drawable.bg_common)          // 状态栏/导航栏背景 drawable
//        // 设置背景的方法三选一即可
//
//        val config = BarConfig.newInstance()            // 创建配置对象
//            .fitWindow(true)                  // 布局是否侵入状态栏（true 不侵入，false 侵入）
//            .background(background)                     // 设置 background 对象
//            .light(false)
//
//        UltimateBarX.with(this)                  // 对当前 Activity 或 Fragment 生效
//            .config(config)                               // 使用配置
//            .applyStatusBar()
    }
}