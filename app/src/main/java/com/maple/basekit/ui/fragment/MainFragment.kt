package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMainBinding
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MainFragment(val viewModel: HomeViewModel): BaseViewFragment<FragmentMainBinding, HomeViewModel>() {

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
        // super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .color(UIUtils.getColor(R.color.common_white))
            .light(true)
            .applyStatusBar()
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
        setTitle<MainFragment>("首页").setTxtColor<MainFragment>(R.color.common_black).onUseBack<MainFragment>(false).setTitleBarBackground<MainFragment>(R.color.common_white)
    }
}