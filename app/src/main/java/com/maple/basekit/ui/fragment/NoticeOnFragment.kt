package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentNoticeOnBinding
import com.maple.basekit.vm.NoticeViewModel
import com.maple.baselib.utils.LogUtils
import com.maple.common.base.BaseViewFragment
import com.zy.multistatepage.MultiStateContainer

class NoticeOnFragment(val viewModel: NoticeViewModel): BaseViewFragment<FragmentNoticeOnBinding, NoticeViewModel>() {

    override fun hasUsedStateView(): Boolean = true

    companion object {
        @JvmStatic
        fun getInstance(viewModel: NoticeViewModel): NoticeOnFragment {
            return NoticeOnFragment(viewModel)
        }
    }

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_notice_on

    override fun initData(savedInstanceState: Bundle?) {
        onStateError()

    }

    override fun onRestLoad() {
        super.onRestLoad()
        LogUtils.logGGQ("已处理--->onRestLoad")

    }

    override fun onStateRetry(container: MultiStateContainer?) {
        super.onStateRetry(container)
        showToast("点击重试")
    }
}
