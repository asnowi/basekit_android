package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMineBinding
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment

class MineFragment(val viewModel: HomeViewModel) : BaseViewFragment<FragmentMineBinding, HomeViewModel>() {

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
        super.setStatusBarMode(color)
    }

    companion object {
        @JvmStatic
        fun getInstance(viewModel: HomeViewModel): MineFragment {
            return MineFragment(viewModel)
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }



    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData(savedInstanceState: Bundle?) {

    }
}