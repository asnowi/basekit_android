package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentNoticeUnBinding
import com.maple.basekit.vm.NoticeViewModel
import com.maple.baselib.utils.LogUtils
import com.maple.common.base.BaseViewFragment

class NoticeUnFragment(val viewModel: NoticeViewModel): BaseViewFragment<FragmentNoticeUnBinding, NoticeViewModel>() {

    override fun hasUsedStateView(): Boolean = true

    companion object {
        @JvmStatic
        fun getInstance(viewModel: NoticeViewModel): NoticeUnFragment {
            return NoticeUnFragment(viewModel)
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_notice_un

    override fun initData(savedInstanceState: Bundle?) {
        onStateEmpty()
    }

    override fun onRestLoad() {
        super.onRestLoad()
        LogUtils.logGGQ("未处理--->onRestLoad")

    }

}