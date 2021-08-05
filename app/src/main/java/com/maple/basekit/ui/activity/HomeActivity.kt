package com.maple.basekit.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.maple.basekit.R
import com.maple.basekit.databinding.ActivityHomeBinding
import com.maple.basekit.ui.fragment.MainFragment
import com.maple.basekit.ui.fragment.MineFragment
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewActivity
import com.maple.common.common.MyFragmentStateAdapter
import com.maple.common.widget.view.NavTabView
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class HomeActivity : BaseViewActivity<ActivityHomeBinding, HomeViewModel>() {

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
        //super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .color(UIUtils.getColor(R.color.common_white))
            .light(true)
            .applyStatusBar()
    }

    private var lastBackPressedMillis: Long = 0

    private val viewModel by viewModels<HomeViewModel>()

    private val list: List<Fragment> by lazy { listOf<Fragment>(
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
        binding.pager.currentItem = 0
        binding.navTab.setSelectedPosition(0, false)
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
               // binding.bnav.menu.getItem(position).isChecked = true
            }
        })
        binding.navTab.setOnTabSelectedListener(object :NavTabView.TabSelectedListener{
            override fun onTabSelected(position: Int) {
                binding.pager.currentItem = position
                binding.navTab.setSelectedPosition(position, true)
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabReselected(position: Int) {
            }
        })

//        binding.bnav.setOnNavigationItemSelectedListener { item ->
//            when(item.itemId){
//                R.id.item_nav_main ->{
//                    binding.pager.currentItem = 0
//                }
//                R.id.item_nav_mine ->{
//                    binding.pager.currentItem = 1
//                }
//            }
//            false
//        }
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