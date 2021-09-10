package com.maple.basekit.ui.activity

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.ActivityDemoBinding
import com.maple.basekit.vm.DemoViewModel
import com.maple.common.base.BaseViewActivity

class DemoActivity : BaseViewActivity<ActivityDemoBinding, DemoViewModel>() {

    private val viewModel by viewModels<DemoViewModel>()


    override fun getLayoutId(): Int = R.layout.activity_demo

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }



    override fun initData(savedInstanceState: Bundle?) {

    }

}