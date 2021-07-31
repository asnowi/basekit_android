package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentNoticeOnBinding
import com.maple.basekit.vm.NoticeViewModel
import com.maple.common.base.BaseViewFragment

class NoticeOnFragment : BaseViewFragment<FragmentNoticeOnBinding, NoticeViewModel>() {

    private val viewModel by viewModels<NoticeViewModel>()

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_notice_on

    override fun initData(savedInstanceState: Bundle?) {

    }

}