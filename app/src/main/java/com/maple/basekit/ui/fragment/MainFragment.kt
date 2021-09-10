package com.maple.basekit.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMainBinding
import com.maple.basekit.model.entity.BannerEntity
import com.maple.basekit.model.entity.MainEntity
import com.maple.basekit.ui.activity.DemoActivity
import com.maple.basekit.ui.adapter.MainAdapter
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.common.base.BaseViewFragment
import com.maple.common.common.ScrollWebViewActivity
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MainFragment: BaseViewFragment<FragmentMainBinding, HomeViewModel>() {
    private val viewModel by viewModels<HomeViewModel>()

    override fun hasUsedStateView(): Boolean = true

    override fun hasStatusBarMode(): Boolean = true

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
                    //showToast(it.name)
                    when(pos) {
                        0 -> {
                            startActivity(Intent(requireContext(),DemoActivity::class.java))
                        }
                        1 -> {
                            startActivity(Intent(requireContext(),ScrollWebViewActivity::class.java))
                        }
                    }
                }
            }
        })
    } }


    override fun setStatusBarMode(color: Int) {
        // super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .color(UIUtils.getColor(R.color.common_toolbar))
            .light(true)
            .applyStatusBar()
    }

    companion object {
        @JvmStatic
        fun getInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int = R.layout.fragment_main


    override fun isEnableRefresh(): Boolean = true

    override fun initData(savedInstanceState: Bundle?) {
        setTitle<MainFragment>("首页").setTxtColor<MainFragment>(R.color.common_white)
            .onUseBack<MainFragment>(false)

        bannerList.clear()
        bannerList.add(BannerEntity("测试1", resId = R.drawable.welcome1, type = 1))
        bannerList.add(BannerEntity("测试2", resId = R.drawable.welcome2, type = 1))
        bannerList.add(BannerEntity("测试3", resId = R.drawable.welcome3, type = 1))
        bannerList.add(BannerEntity("测试4", resId = R.drawable.welcome4, type = 1))

        menuList.clear()
        menuList.add(MainEntity("首页1", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=1.jpg"))
        menuList.add(MainEntity("首页2", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=2.jpg"))
        menuList.add(MainEntity("首页3", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=3.jpg"))
        menuList.add(MainEntity("首页4", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=4.jpg"))

        recyclerView?.let {
            val layoutManage: GridLayoutManager = GridLayoutManager(requireContext(), 2)
            layoutManage.spanSizeLookup = (object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if(position >= 1) 1 else 2
                }
            })
            it.layoutManager = layoutManage
            it.adapter = mainAdapter
        }

        mainAdapter.setBannerData(bannerList)
        mainAdapter.setListData(menuList)
    }

    private val newList: MutableList<MainEntity> = mutableListOf()

    override fun onRefreshData() {
        super.onRefreshData()
        newList.clear()
        newList.add(MainEntity("新首页${mainAdapter.itemCount}", url = "https://img1.baidu.com/it/u=617911352,2488546121&fm=26&fmt=auto&gp=5.jpg"))
        mainAdapter.updateListData(newList)
        finishRefresh()
    }


    override fun onRestLoad() {
        super.onRestLoad()
    }

}