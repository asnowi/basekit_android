package com.maple.basekit.ui.activity

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.maple.basekit.R
import com.maple.basekit.databinding.ActivityNoticeBinding
import com.maple.basekit.ui.fragment.NoticeOnFragment
import com.maple.basekit.ui.fragment.NoticeUnFragment
import com.maple.basekit.vm.NoticeViewModel
import com.maple.common.base.BaseViewActivity
import com.maple.common.common.MyFragmentStateAdapter

class NoticeActivity : BaseViewActivity<ActivityNoticeBinding, NoticeViewModel>() {
    private val tabTitle: List<String> by lazy {
        listOf("未处理", "已处理")
    }
    private val viewModel by viewModels<NoticeViewModel>()

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_notice

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<NoticeActivity>("通知").onBack<NoticeActivity> {
            onFinish()
        }

        val list: List<Fragment> = listOf(
            NoticeUnFragment.getInstance(viewModel),
            NoticeOnFragment.getInstance(viewModel)
        )

        binding.pager.adapter = MyFragmentStateAdapter(this, list)
        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        tabTitle.forEachIndexed { index, s ->
            val tab: TabLayout.Tab = binding.tab.newTab().setCustomView(R.layout.tab_item)
            tab.customView?.findViewById<TextView>(R.id.tv_title)?.text = s
            binding.tab.addTab(tab)
        }
    }
}