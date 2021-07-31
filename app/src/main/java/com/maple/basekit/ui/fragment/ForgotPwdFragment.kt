package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentForgotPwdBinding
import com.maple.basekit.vm.AccountViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment

class ForgotPwdFragment : BaseViewFragment<FragmentForgotPwdBinding, AccountViewModel>() {

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
        super.setStatusBarMode(color)
    }

    private val viewModel by viewModels<AccountViewModel>()

    override fun hasNavController(): Boolean = true

    override fun getLayoutId(): Int = R.layout.fragment_forgot_pwd


    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ForgotPwdFragment>("找回密码").onBack<ForgotPwdFragment> { onPopBack() }.setTitleBarBackground<ForgotPwdFragment>(R.color.common_white)
    }

}