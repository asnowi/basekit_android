package com.maple.basekit.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMineBinding
import com.maple.basekit.ui.activity.HomeActivity
import com.maple.basekit.ui.activity.NoticeActivity
import com.maple.basekit.ui.activity.WelcomeActivity
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.baselib.widget.imageloader.TransType
import com.maple.baselib.widget.imageloader.glide.GlideImageConfig
import com.maple.common.base.BaseActivity
import com.maple.common.base.BaseViewFragment
import com.maple.common.ext.loadConfigImage
import com.maple.common.ext.toGone
import com.maple.common.ext.toVisible
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MineFragment(val viewModel: HomeViewModel) : BaseViewFragment<FragmentMineBinding, HomeViewModel>(){
    private var isShowLoading = false
    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
        //super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .transparent()
            .light(true)
            .applyStatusBar()
    }

    companion object {
        @JvmStatic
        fun getInstance(viewModel: HomeViewModel): MineFragment {
            return MineFragment(viewModel)
        }
    }

    override fun bindViewModel() {
        binding.viewModel = viewModel
    }


    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initData(savedInstanceState: Bundle?) {

        viewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })

        viewModel.noticeEvent.observe(this, Observer {
            (activity as BaseActivity).onStartActivity(NoticeActivity::class.java)
        })

        viewModel.loadingEvent.observe(this, {
            if(isShowLoading) {
                isShowLoading = false
                dismissLoading()
            } else {
                isShowLoading = true
                showLoading()
            }
        })
    }

}