package com.maple.common.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.ColorRes
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import com.gyf.immersionbar.ktx.immersionBar
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.common.R
import com.maple.common.ext.toGone
import com.maple.common.ext.toVisible
import com.maple.common.utils.ToastUtils
import com.maple.common.widget.dialog.LoadingDialog
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.maple.baselib.base.BaseFragment as B


abstract class BaseFragment: B(), ImmersionOwner {

    /**
     * ImmersionBar代理类
     */
    private val immersionProxy: ImmersionProxy by lazy { ImmersionProxy(this) }

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
        toolbar?.setBackgroundColor(color)
        return this as T
    }

    //------------end------------------

    @Suppress("DEPRECATION")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        immersionProxy.setUserVisibleHint(isVisibleToUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionProxy.onCreate(savedInstanceState)
    }

    @Suppress("DEPRECATION")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        immersionProxy.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        immersionProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        immersionProxy.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        immersionProxy.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        immersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        immersionProxy.onConfigurationChanged(newConfig)
    }



    override fun onLazyAfterView() {
        LogUtils.logGGQ("ImmersionBar------>>>onLazyAfterView")
    }

    override fun onInvisible() {
        LogUtils.logGGQ("ImmersionBar------>>>onInvisible")

    }

    override fun onLazyBeforeView() {
        LogUtils.logGGQ("ImmersionBar------>>>onLazyBeforeView")

    }

    override fun immersionBarEnabled(): Boolean {
        LogUtils.logGGQ("ImmersionBar------>>>immersionBarEnabled")
        return hasStatusBarMode()
    }

    override fun onVisible() {
        LogUtils.logGGQ("ImmersionBar------>>>onVisible")

    }


    override fun initImmersionBar() {
        LogUtils.logGGQ("ImmersionBar------>>>initImmersionBar")
        setStatusBarMode()
    }

    override fun setStatusBarMode(color: Int) {
        super.setStatusBarMode(color)
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(true)
            .navigationBarColor(R.color.common_white)
            .navigationBarDarkIcon(true)
            .init()
    }
}