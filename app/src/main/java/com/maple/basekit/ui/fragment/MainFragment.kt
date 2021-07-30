package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMainBinding
import com.maple.basekit.vm.HomeViewModel
import com.maple.common.base.BaseViewFragment

class MainFragment(val viewModel: HomeViewModel): BaseViewFragment<FragmentMainBinding, HomeViewModel>() {

    override fun hasStatusBarMode(): Boolean = true
    override fun setStatusBarMode(color: Int) {
        super.setStatusBarMode(color)
    }

    companion object {
        @JvmStatic
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun bindViewModel() {
        binding.viewModel = this.viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {

    }
}