package com.maple.basekit.ui.activity

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.ActivityNoticeBinding
import com.maple.basekit.vm.NoticeViewModel
import com.maple.common.base.BaseViewActivity

class NoticeActivity : BaseViewActivity<ActivityNoticeBinding, NoticeViewModel>() {

    private val viewModel by viewModels<NoticeViewModel>()

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_notice

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<NoticeActivity>("通知").onBack<NoticeActivity> {
            onFinish()
        }
    }


}