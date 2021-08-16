package com.maple.basekit.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.facebook.shimmer.ShimmerDrawable
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMineBinding
import com.maple.basekit.db.DBHelper
import com.maple.basekit.db.UserInfo
import com.maple.basekit.ui.activity.AccountActivity
import com.maple.basekit.ui.activity.NoticeActivity
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.UIUtils
import com.maple.baselib.widget.imageloader.TransType
import com.maple.baselib.widget.imageloader.glide.GlideImageConfig
import com.maple.common.base.BaseActivity
import com.maple.common.base.BaseViewFragment
import com.maple.common.ext.loadConfigImage
import com.maple.common.ext.loadImage
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MineFragment(val viewModel: HomeViewModel) :
    BaseViewFragment<FragmentMineBinding, HomeViewModel>() {
    private var isShowLoading = false
    override fun hasStatusBarMode(): Boolean = true

    private var userInfo: UserInfo? = null

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
        userInfo = DBHelper.getUserInfo()

        setUserView()
        viewModel.defUI.toastEvent.observe(this, Observer {
            showToast(it)
        })

        viewModel.noticeEvent.observe(this, Observer {
            if(userInfo != null) {
                (activity as BaseActivity).onStartActivity(NoticeActivity::class.java)
            } else {
                val intent: Intent = Intent(requireActivity(),AccountActivity::class.java)
                intent.putExtra("flag","slide")
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity()).toBundle())
            }
        })

        viewModel.loadingEvent.observe(this, {
            if (isShowLoading) {
                isShowLoading = false
                dismissLoading()
            } else {
                isShowLoading = true
                showLoading()
            }
        })

        viewModel.logoutEvent.observe(this, Observer {
            SPUtils.getInstance().clear()
            DBHelper.removeUser()
            userInfo = null
            setUserView()
        })
    }


    private fun setUserView() {
        if(userInfo != null) {
            binding.tvName.text = userInfo!!.phone
            binding.ivAvatar.loadConfigImage(userInfo!!.avatar, config = GlideImageConfig(userInfo!!.avatar,binding.ivAvatar,loadingDrawable = ShimmerDrawable()).apply { type = TransType.CIRCLE })
        } else {
            binding.tvName.text = "请登录"
            binding.ivAvatar.loadImage(R.drawable.ic_default_avatar)
        }
    }

}