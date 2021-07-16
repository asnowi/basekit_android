package com.maple.common.base

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.maple.baselib.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseViewActivity<VB : ViewDataBinding, VM : BaseViewModel>: com.maple.common.base.BaseActivity(), CoroutineScope by MainScope() {

    abstract fun onBindViewModel(): Class<VM>

    protected val binding: VB by lazy { DataBindingUtil.setContentView(this, getLayoutId()) as VB }

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(onBindViewModel()) }

    override fun setContentLayout() {
        super.setContentLayout()
        this.binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        this.binding.unbind()
    }

}