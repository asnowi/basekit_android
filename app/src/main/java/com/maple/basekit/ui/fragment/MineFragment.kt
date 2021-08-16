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
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.UIUtils
import com.maple.baselib.widget.imageloader.TransType
import com.maple.baselib.widget.imageloader.glide.GlideImageConfig
import com.maple.common.base.BaseActivity
import com.maple.common.base.BaseViewFragment
import com.maple.common.ext.loadConfigImage
import com.maple.common.ext.loadImage
import com.maple.common.widget.dialog.CommonDialog
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.enabled

class MineFragment(val viewModel: HomeViewModel) :
    BaseViewFragment<FragmentMineBinding, HomeViewModel>() {
    private var isShowLoading = false
    override fun hasStatusBarMode(): Boolean = true

    private val logoutDialog by lazy { CommonDialog(requireActivity(),content = "确定退出账号",listener = object :
        CommonDialog.OnClickListener{
        override fun onConfirmClick() {
            clearUserInfo()
        } }) }

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

        viewModel.userInfoEvent.observe(this, Observer {
            if(it != null) {
                LogUtils.logGGQ("--已登录--")
                binding.tvName.text = it.phone
                binding.ivAvatar.loadConfigImage(it.avatar, config = GlideImageConfig(it.avatar,binding.ivAvatar,loadingDrawable = ShimmerDrawable()).apply { type = TransType.CIRCLE })
                binding.btnLogout.apply {
                    text = "退出登录"
                    background = UIUtils.getDrawable(R.drawable.shape_common_enable)
                }

            } else {
                LogUtils.logGGQ("--未登录--")
                binding.tvName.text = "请登录"
                binding.ivAvatar.loadImage(R.drawable.ic_default_avatar)
                binding.btnLogout.apply {
                    text = "点击登录"
                    background = UIUtils.getDrawable(R.drawable.selector_common)
                }
            }
        })

        viewModel.noticeEvent.observe(this, Observer {
            if(viewModel.userInfoEvent.value != null) {
                (activity as BaseActivity).onStartActivity(NoticeActivity::class.java)
            } else {
                openLogin()
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
            if(viewModel.userInfoEvent.value != null) {
                logoutDialog.show()
            } else {
                openLogin()
            }
        })
    }

    override fun onRestLoad() {
        super.onRestLoad()
        viewModel.setUserInfo()
    }


    private fun clearUserInfo () {
        SPUtils.getInstance().clear()
        DBHelper.removeUser()
        viewModel.userInfoEvent.value = null
    }

    private fun openLogin() {
        val intent: Intent = Intent(requireActivity(),AccountActivity::class.java)
        intent.putExtra("flag","slide")
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity()).toBundle())
    }
}