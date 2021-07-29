package com.maple.baselib.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maple.baselib.app.ResultCode
import com.maple.baselib.app.manager.SingleLiveEvent
import com.maple.baselib.http.error.ERROR
import com.maple.baselib.http.error.ExceptionHandle
import com.maple.baselib.http.error.ResponseThrowable
import com.maple.baselib.http.resp.BaseResp
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.NetworkUtil
import com.maple.baselib.utils.UIUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

open class BaseViewModel: ViewModel(), LifecycleObserver {

    val defUI: UIChange by lazy { UIChange() }

    fun onClickProxy(m: () -> Unit) {
        if (!UIUtils.isFastClick()) {
            m()
        }
    }

    /**
     * 所有网络请求都在 viewModelScope 域中启动，当页面销毁时会自动
     * 调用ViewModel的  #onCleared 方法取消所有协程
     */
    private fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch { block() }

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
            isShowDialog: Boolean = true,
            isShowToast: Boolean = true,
            block: suspend CoroutineScope.() -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit = {
                LogUtils.logGGQ("--isShowToast-->${isShowToast}")
                LogUtils.logGGQ("--error-->${it.errMsg}")
                if(isShowToast) defUI.onError(it)
            },
            complete: suspend CoroutineScope.() -> Unit = {}
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
    fun <T> launchOnlyResult(
            isShowDialog: Boolean = true,
            isShowToast: Boolean = true,
            block: suspend CoroutineScope.() -> BaseResp<T>,
            success: (T?) -> Unit,
            error: (ResponseThrowable) -> Unit = {
                LogUtils.logGGQ("--isShowToast--->${isShowToast}")
                LogUtils.logGGQ("--error--->${it.errMsg}")
                if(isShowToast) defUI.onError(it)
            },
            complete: () -> Unit = {}
    ) {
        if (!NetworkUtil.isConnected()) {
            defUI.onError(ResponseThrowable(ERROR.NETWORD_UNCONNECTED))
            return
        }
        if (isShowDialog) defUI.onShowDialog()
        launchUI {
            handleException(
                    { withContext(Dispatchers.IO) { block() } },
                    { res ->
                        executeResponse(res) {
                            success(it)
                        }
                    },
                    {
                        error(it)
                    },
                    {
                        if (isShowDialog) defUI.onDismissDialog()
                        complete()
                    }
            )
        }
    }


    /**
     * 请求结果过滤
     */
    private suspend fun <T> executeResponse(
            response: BaseResp<T>,
            success: suspend CoroutineScope.(T?) -> Unit
    ) {
        coroutineScope {
            if (ResultCode.isSuccess(response.code)) {
                success(response.data)
            } else {
                throw ResponseThrowable(response.code, response.msg)
            }
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
     * 异常统一处理
     */
    private suspend fun <T> handleException(
            block: suspend CoroutineScope.() -> BaseResp<T>,
            success: suspend CoroutineScope.(BaseResp<T>) -> Unit,
            error: suspend CoroutineScope.(ResponseThrowable) -> Unit,
            complete: suspend CoroutineScope.() -> Unit
    ) {
        try {
            coroutineScope {
                try {
                    success(block())
                    LogUtils.logGGQ("-->>-T-T-<<")
                } catch (e: ResponseThrowable) {
                    error(ExceptionHandle.handleException(e, e))
                } finally {
                    complete()
                }
            }
        } catch (e: ResponseThrowable) {
            e.fillInStackTrace()
            LogUtils.logGGQ("异常--e->${e.message}")
            error(ExceptionHandle.handleException(e, e))
        }
    }





    /**
     * UI事件
     */
    inner class UIChange{

        val toastEvent by lazy { SingleLiveEvent<String>() }
        fun onError(e: ResponseThrowable) {
            LogUtils.logGGQ(e.errMsg)
            toastEvent.postValue(e.errMsg)
        }

        val showDialog by lazy { SingleLiveEvent<Any>() }
        fun onShowDialog() {
            showDialog.call()
        }

        val dismissDialog by lazy { SingleLiveEvent<Any>() }
        fun onDismissDialog () {
            dismissDialog.call()
        }
    }


    override fun onCleared() {
        super.onCleared()
        LogUtils.logGGQ("VM ->> onCleared")
    }
}