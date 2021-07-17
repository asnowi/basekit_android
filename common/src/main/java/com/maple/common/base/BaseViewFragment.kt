package com.maple.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.maple.baselib.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseViewFragment<VB : ViewDataBinding, VM : BaseViewModel>: com.maple.common.base.BaseFragment(), CoroutineScope by MainScope() {


    inline fun <reified VM : ViewModel> viewModels(): Lazy<VM> {
        return lazy {
            ViewModelProvider(requireActivity()).get(VM::class.java)
        }
    }

    protected lateinit var binding: VB

    open fun hasNavController(): Boolean = false

    protected var navController: NavController? = null


    abstract fun bindViewModel()

    override fun setContentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return this.binding.root
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        this.binding.lifecycleOwner = this
        this.bindViewModel()
        if(hasNavController()) {
            this.navController = Navigation.findNavController(view)
        }
    }

    /**
     * 返回
     */
    open fun onPopBack(){
        if(hasNavController()){
            this.navController?.popBackStack()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
        this.binding.unbind()
    }
}