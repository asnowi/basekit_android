package com.maple.basekit.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMainBinding
import com.maple.basekit.model.entity.BannerEntity
import com.maple.basekit.model.entity.MainEntity
import com.maple.basekit.ui.adapter.MainAdapter
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MainFragment(val viewModel: HomeViewModel) :
    BaseViewFragment<FragmentMainBinding, HomeViewModel>() {

    override fun hasUsedStateView(): Boolean = true

    private val bannerList: MutableList<BannerEntity> = mutableListOf()
    private val menuList: MutableList<MainEntity> = mutableListOf()

    private val mainAdapter: MainAdapter by lazy { MainAdapter(requireContext()).apply {
        setListener(object: MainAdapter.OnClickListener{
            override fun onBannerItemClick(pos: Int, item: BannerEntity?) {
                item?.let {
                    showToast(it.name)
                }
            }

            override fun onListItemClick(pos: Int, item: MainEntity?) {
                item?.let {
                    showToast(it.name)
                }
            }
        })
    } }

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
        setTitle<MainFragment>("首页").setTxtColor<MainFragment>(R.color.common_white)
            .onUseBack<MainFragment>(false)


        bannerList.clear()
        bannerList.add(BannerEntity("测试1", resId = R.drawable.welcome1, type = 1))
        bannerList.add(BannerEntity("测试2", resId = R.drawable.welcome2, type = 1))
        bannerList.add(BannerEntity("测试3", resId = R.drawable.welcome3, type = 1))
        bannerList.add(BannerEntity("测试4", resId = R.drawable.welcome4, type = 1))

        menuList.clear()
        menuList.add(MainEntity("测试1", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=0.jpg"))
        menuList.add(MainEntity("测试2", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=0.jpg"))
        menuList.add(MainEntity("测试3", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=0.jpg"))
        menuList.add(MainEntity("测试4", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=0.jpg"))

        binding.commonRecyclerView.apply {
            val layoutManage: GridLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManage.spanSizeLookup = (object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if(position >= 1) 1 else 2
                }
            })
            layoutManager = layoutManage
            adapter = mainAdapter
        }

        mainAdapter.setBannerData(bannerList)
        mainAdapter.setListData(menuList)

    }
}