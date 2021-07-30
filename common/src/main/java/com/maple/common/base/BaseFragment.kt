package com.maple.common.base

import android.content.res.Configuration
import android.os.Bundle
import com.gyf.immersionbar.components.ImmersionOwner
import com.gyf.immersionbar.components.ImmersionProxy
import com.gyf.immersionbar.ktx.immersionBar
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.common.utils.ToastUtils
import com.maple.common.widget.dialog.LoadingDialog
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


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        immersionProxy.setUserVisibleHint(isVisibleToUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionProxy.onCreate(savedInstanceState)
    }

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
    }
}