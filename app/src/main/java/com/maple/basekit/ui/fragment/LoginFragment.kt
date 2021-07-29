package com.maple.basekit.ui.fragment

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentLoginBinding
import com.maple.basekit.ui.activity.HomeActivity
import com.maple.basekit.vm.AccountViewModel
import com.maple.baselib.utils.LogUtils
import com.maple.common.base.BaseActivity
import com.maple.common.base.BaseViewFragment
import com.maple.common.ext.afterTextChanged
import com.maple.common.utils.StringUtils
import com.maple.common.widget.view.EyeEditText

class LoginFragment: BaseViewFragment<FragmentLoginBinding, AccountViewModel>() {

    private val viewModel by viewModels<AccountViewModel>()

    override fun hasNavController(): Boolean = true

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun initData(savedInstanceState: Bundle?) {
        viewModel.defUI.showDialog.observe(this, Observer {
            showLoading()
        })

        viewModel.defUI.dismissDialog.observe(this, Observer {
            dismissLoading()
        })

        viewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })

        this.binding.etAccount.afterTextChanged {
            viewModel.account.set(it)
            viewModel.loginState.set(StringUtils.isNotEmpty(viewModel.account.get()) && StringUtils.isNotEmpty(viewModel.password.get()))
        }
        this.binding.etPassword.afterTextChanged {
            viewModel.password.set(it)
            viewModel.loginState.set(StringUtils.isNotEmpty(viewModel.account.get()) && StringUtils.isNotEmpty(viewModel.password.get()))
        }

        this.viewModel.forgotPwdEvent.observe(this, Observer {
            navController?.navigate(R.id.action_loginFragment_to_forgotPwdFragment)
        })

        this.viewModel.loginEvent.observe(this, Observer {
            (activity as BaseActivity).onStartActivity(HomeActivity::class.java, isFinish = true)
        })

    }

}