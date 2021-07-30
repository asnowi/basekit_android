package com.maple.basekit.ui.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.maple.basekit.R
import com.maple.basekit.databinding.ActivityHomeBinding
import com.maple.basekit.ui.fragment.MainFragment
import com.maple.basekit.ui.fragment.MineFragment
import com.maple.basekit.vm.HomeViewModel
import com.maple.common.base.BaseViewActivity
import com.maple.common.common.MyFragmentStateAdapter

class HomeActivity : BaseViewActivity<ActivityHomeBinding, HomeViewModel>() {

    private var lastBackPressedMillis: Long = 0

    private val viewModel by viewModels<HomeViewModel>()

    private val list by lazy { listOf(
        MainFragment.getInstance(viewModel),
        MineFragment.getInstance(viewModel)
    ) }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun bindViewModel() {
        this.binding.viewModel = viewModel
    }


    override fun initData(savedInstanceState: Bundle?) {
        binding.pager.isUserInputEnabled = false
        val adapter: MyFragmentStateAdapter = MyFragmentStateAdapter(this, list)
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = adapter
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bnav.menu.getItem(position).isChecked = true
            }
        })
        binding.bnav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_nav_main ->{
                    binding.pager.currentItem = 0
                }
                R.id.item_nav_mine ->{
                    binding.pager.currentItem = 1
                }
            }
            false
        }
    }

    override fun hasEventKeyBack(): Boolean = true


    override fun onKeyBack(keyCode: Int) {
        if (lastBackPressedMillis + 2000L > System.currentTimeMillis()) {
            //moveTaskToBack(true)
            this@HomeActivity.finish()
        } else {
            lastBackPressedMillis = System.currentTimeMillis()
            showToast("再按一次退出程序")
        }
        super.onKeyBack(keyCode)
    }

}