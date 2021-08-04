package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentForgotPwdBinding
import com.maple.basekit.vm.AccountViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class ForgotPwdFragment : BaseViewFragment<FragmentForgotPwdBinding, AccountViewModel>() {

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
       // super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .color(UIUtils.getColor(R.color.common_white))
            .light(true)
            .applyStatusBar()
    }

    private val viewModel by viewModels<AccountViewModel>()

    override fun hasNavController(): Boolean = true

    override fun getLayoutId(): Int = R.layout.fragment_forgot_pwd

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<ForgotPwdFragment>("找回密码").setTxtColor<ForgotPwdFragment>(R.color.common_black).onBack<ForgotPwdFragment> { onPopBack() }.setTitleBarBackground<ForgotPwdFragment>(R.color.common_white)
    }

}