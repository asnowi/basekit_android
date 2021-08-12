package com.maple.basekit.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maple.basekit.R
import com.maple.basekit.databinding.ActivityNoticeBinding
import com.maple.basekit.ui.fragment.NoticeOnFragment
import com.maple.basekit.ui.fragment.NoticeUnFragment
import com.maple.basekit.vm.NoticeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewActivity
import com.maple.common.common.MyFragmentStateAdapter
import org.jetbrains.anko.textColor

class NoticeActivity : BaseViewActivity<ActivityNoticeBinding, NoticeViewModel>() {
    private val tabTitle: List<String> by lazy {
        listOf("未处理", "已处理")
    }

    private var mediator: TabLayoutMediator? = null
    private var callback: ViewPager2.OnPageChangeCallback? = null

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

        callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.pager.currentItem = position
            }
        }
        binding.pager.apply {
            adapter = MyFragmentStateAdapter(this@NoticeActivity, list)
            callback?.let { this.registerOnPageChangeCallback(it) }
//            this.setPageTransformer(pageTrans)
        }

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        TabLayoutMediator(binding.tab, binding.pager) { tab, position ->
            tab.customView = setTabView(position)
        }.attach()
    }

    private fun setTabView(position: Int): View {
        val view: View = this.layoutInflater.inflate(R.layout.tab_item, binding.tab, false)
        view.findViewById<TextView>(R.id.tv_title)?.apply {
            text = tabTitle.get(position)
            textColor = UIUtils.getColor(R.color.common_primary)
        }
        return view
    }

    override fun onDestroy() {
        mediator?.detach()
        callback?.let {
            binding.pager.unregisterOnPageChangeCallback(it)
        }
        super.onDestroy()
    }
}