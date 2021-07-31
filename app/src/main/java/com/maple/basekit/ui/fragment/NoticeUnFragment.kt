package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentNoticeUnBinding
import com.maple.basekit.vm.NoticeViewModel
import com.maple.common.base.BaseViewFragment

class NoticeUnFragment : BaseViewFragment<FragmentNoticeUnBinding, NoticeViewModel>() {

    private val viewModel by viewModels<NoticeViewModel>()

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_notice_un

    override fun initData(savedInstanceState: Bundle?) {

    }

}