package com.maple.basekit.ui.fragment

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMainBinding
import com.maple.basekit.model.entity.BannerEntity
import com.maple.basekit.ui.adapter.MyBannerAdapter
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.indicator.RoundLinesIndicator
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MainFragment(val viewModel: HomeViewModel): BaseViewFragment<FragmentMainBinding, HomeViewModel>() {

    private val bannerList: MutableList<BannerEntity> = mutableListOf()

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
        // super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .color(UIUtils.getColor(R.color.common_toolbar))
            .light(true)
            .applyStatusBar()
    }

    companion object {
        @JvmStatic
        fun getInstance(viewModel: HomeViewModel): MainFragment {
            return MainFragment(viewModel)
        }
    }

    override fun bindViewModel() {
        binding.viewModel = this.viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<MainFragment>("首页").setTxtColor<MainFragment>(R.color.common_white).onUseBack<MainFragment>(false)
        bannerList.clear()
        bannerList.add(BannerEntity("测试1",resId = R.drawable.welcome1, type = 1))
        bannerList.add(BannerEntity("测试2",resId = R.drawable.welcome2, type = 1))
        bannerList.add(BannerEntity("测试3",resId = R.drawable.welcome3, type = 1))
        bannerList.add(BannerEntity("测试4",resId = R.drawable.welcome4, type = 1))
        val bannerAdapter: MyBannerAdapter = MyBannerAdapter(requireContext(), bannerList)
        binding.banner.addBannerLifecycleObserver(this)
            .setAdapter(bannerAdapter)
            .setIndicator(RectangleIndicator(requireContext()))
    }
}