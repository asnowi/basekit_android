package com.maple.baselib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maple.baselib.utils.LogUtils

abstract class BaseFragment: Fragment(), IView{

    abstract fun getLayoutId(): Int

    abstract fun initData(savedInstanceState: Bundle?): Unit

    open fun initView(view: View, savedInstanceState: Bundle?){}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return setContentLayout(inflater, container, savedInstanceState)
    }

    open fun setContentLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        LogUtils.logGGQ("---->setContentLayout")
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initView(view, savedInstanceState)
        this.initData(savedInstanceState)
    }

}