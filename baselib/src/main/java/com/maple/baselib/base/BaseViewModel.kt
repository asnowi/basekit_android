package com.maple.baselib.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maple.baselib.app.ResultCode
import com.maple.baselib.http.error.ERROR
import com.maple.baselib.http.error.ExceptionHandle
import com.maple.baselib.http.error.ResponseThrowable
import com.maple.baselib.http.resp.BaseResp
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.NetworkUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class BaseViewModel: ViewModel(), LifecycleObserver {

    val defUI: UIChange by lazy { UIChange() }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

    /**
     * 用流的方式进行网络请求
     */
    fun <T> launchFlow(block: suspend () -> T): Flow<T> {
        return flow {
            emit(block())
        }
    }


    /**
     *  不过滤请求结果
     * @param block 请求体
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun launchGo(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {
                if(isShowToast) defUI.onError(it)
            },
            complete: suspend CoroutineScope.() -> Unit = {},
            isShowDialog: Boolean = true,
            isShowToast: Boolean = true
    ) {
        if (!NetworkUtil.isConnected()) {
            defUI.onError(ResponseThrowable(ERROR.NETWORD_UNCONNECTED))
            return
        }
        if (isShowDialog) defUI.onShowDialog()
        launchUI {
            handleException(
                    withContext(Dispatchers.IO) { block },
                    { error(it) },
                    {
                        complete()
                        defUI.onDismissDialog()
                    }
            )
        }
    }


    /**
     * 过滤请求结果，其他全抛异常
     * @param block 请求体
     * @param success 成功回调
     * @param error 失败回调
     * @param complete  完成回调（无论成功失败都会调用）
     * @param isShowDialog 是否显示加载框
     */
    fun <T> launchOnlyresult(
            block: suspend CoroutineScope.() -> BaseResp<T>,
            success: (T?) -> Unit,
            error: (ResponseThrowable) -> Unit = {
                if(isShowToast) defUI.onError(it)
            },
            complete: () -> Unit = {},
            isShowDialog: Boolean = true,
            isShowToast: Boolean = true
    ) {
        if (!NetworkUtil.isConnected()) {
            defUI.onError(ResponseThrowable(ERROR.NETWORD_UNCONNECTED))
            return
        }
        if (isShowDialog) defUI.onShowDialog()
        launchUI {
            handleException(
                    {
                        withContext(Dispatchers.IO) {
                            block().let {
                                if (ResultCode.isSuccess(it.code)) {
                                    it.data
                                } else throw ResponseThrowable(it.code, it.msg)
                            }
                        }.also { success(it) }
                    },
                    { error(it) },
                    {
                        complete()
                        defUI.onDismissDialog()
                    }
            )
        }
    }


    /**
     * 异常统一处理
     */
    private suspend fun handleException(
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            try {
                block()
                LogUtils.logGGQ("-->>-N-T-<<")
            } catch (e: ResponseThrowable) {
                error(ExceptionHandle.handleException(e, e))
            } finally {
                complete()
            }
        }
    }

    /**
     * UI事件
     */
    inner class UIChange{

        fun onError(e: ResponseThrowable) {
            LogUtils.logGGQ(e.errMsg)
        }

        fun onShowDialog() {}
        fun onDismissDialog () {}
    }


    override fun onCleared() {
        super.onCleared()
        LogUtils.logGGQ("VM ->> onCleared")
    }
}