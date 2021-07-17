package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentForgotPwdBinding
import com.maple.basekit.vm.AccountViewModel
import com.maple.common.base.BaseViewFragment

class ForgotPwdFragment : BaseViewFragment<FragmentForgotPwdBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()

    override fun hasNavController(): Boolean = true

    override fun getLayoutId(): Int = R.layout.fragment_forgot_pwd


    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}