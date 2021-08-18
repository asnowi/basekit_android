package com.maple.basekit.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.*
import com.facebook.shimmer.ShimmerDrawable
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.maple.basekit.R
import com.maple.basekit.databinding.FragmentMineBinding
import com.maple.basekit.db.DBHelper
import com.maple.basekit.ui.activity.AccountActivity
import com.maple.basekit.ui.activity.NoticeActivity
import com.maple.basekit.vm.HomeViewModel
import com.maple.baselib.utils.LogUtils
import com.maple.baselib.utils.PermissionUtil
import com.maple.baselib.utils.RequestPermission
import com.maple.baselib.utils.UIUtils
import com.maple.baselib.widget.imageloader.ImageLoader
import com.maple.baselib.widget.imageloader.TransType
import com.maple.baselib.widget.imageloader.glide.GlideImageConfig
import com.maple.common.base.BaseActivity
import com.maple.common.base.BaseViewFragment
import com.maple.common.ext.loadConfigImage
import com.maple.common.ext.loadImage
import com.maple.common.widget.dialog.CommonDialog
import com.maple.common.widget.dialog.PictureOptionDialog
import com.maple.common.widget.picture.GlideEngine
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX

class MineFragment: BaseViewFragment<FragmentMineBinding, HomeViewModel>() {
    private val viewModel by viewModels<HomeViewModel>()

    private var isShowLoading = false
    override fun hasStatusBarMode(): Boolean = true

    private val rxPermissions: RxPermissions by lazy { RxPermissions(this) }

    private val logoutDialog by lazy { CommonDialog(requireActivity(),content = "确定退出账号",listener = object :
        CommonDialog.OnClickListener{
        override fun onConfirmClick() {
            clearUserInfo()
        } }) }

    private val pictureDialog by lazy {
        PictureOptionDialog(requireActivity(),object :PictureOptionDialog.OnClickListener{
            override fun onCameraClick(type: Int) {
                //打开相机
//                openCamer(type)
                applyPermissions(type)
            }

            override fun onPhotoClick(type: Int) {
                //打开相册
//                openPhoto(type)
                applyPermissions(type)
            }
        }) }

    override fun setStatusBarMode(color: Int) {
        //super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .transparent()
            .light(true)
            .applyStatusBar()
    }

    companion object {
        @JvmStatic
        fun getInstance(): MineFragment {
            return MineFragment()
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
        viewModel.avatarEvent.observe(this, Observer {
           if(viewModel.userInfoEvent.value != null) {
               pictureDialog.show()
           } else {
               openLogin()
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

    private fun applyPermissions(type: Int){
        PermissionUtil.applyCamera(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                when(type){
                    PictureOptionDialog.TYPE_CAMERA ->{openCamer(type)}
                    PictureOptionDialog.TYPE_PHOTO ->{openPhoto(type)}
                }
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                showPermissionsDialog()
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                showPermissionsDialog()
            }
        }, rxPermissions)
    }

    private var permissionsDialog:CommonDialog? = null
    private fun showPermissionsDialog(){
        permissionsDialog ?: CommonDialog(requireActivity(),
            "权限申请",
            "请开启相机权限，否则您无法正常使用该功能",
            "不同意",
            "前往开启",object :CommonDialog.OnClickListener{
                override fun onCancelClick() {
                    showToast("您已取消")
                }
                override fun onConfirmClick() {
                    AppUtils.launchAppDetailsSettings()
                }
            },isCancelable = true).let {
            if (!it.isShowing) it.show()
        }
    }


    private fun openCamer(type:Int) {
        showToast("访问相机")
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .isCompress(true)// 是否压缩
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.last()?.let {
                        var compressPath:String? = ""
                        if(it.isCompressed && !TextUtils.isEmpty(it.compressPath))
                            compressPath = it.compressPath
                        else{
                            compressPath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                it.androidQToPath
                            }else{
                                it.path
                            }
                        }
                        //上传图片
                        if(!TextUtils.isEmpty(compressPath)){
                            LogUtils.logGGQ("-相机-${type}->>${compressPath}--大小->>${FileUtils.getSize(compressPath)}")
                            binding.ivImg.loadImage(compressPath)
                        }else{
                            showToast("拍照出错,请重新拍照！")
                        }
                    }?:let {
                        showToast("拍照出错,请重新拍照！")
                    }
                }

                override fun onCancel() {
                    showToast("取消")
                }
            })
    }

    private fun openPhoto(type:Int) {
        showToast("访问相册")
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .selectionMode(PictureConfig.SINGLE)
            .isCamera(true)
            .isCompress(true)// 是否压缩
            .isGif(false)
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    result?.last()?.let {
                        var compressPath:String? = ""
                        if(it.isCompressed && !TextUtils.isEmpty(it.compressPath))
                            compressPath = it.compressPath
                        else{
                            compressPath = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                                it.androidQToPath
                            }else{
                                it.path
                            }
                        }
                        //上传图片
                        if(!TextUtils.isEmpty(compressPath)){
                            // showToast("type->${type}-图片大小：${FileUtils.getSize(compressPath)}")
                            LogUtils.logGGQ("-相册-${type}->>${compressPath}--大小->>${FileUtils.getSize(compressPath)}")
                            binding.ivImg.loadImage(compressPath)
                        }else{
                            showToast("选择照片出错,请重新选择！")
                        }
                    }?:let {
                        showToast("选择照片出错,请重新选择！")
                    }
                }

                override fun onCancel() {
                    showToast("取消")
                }
            })
    }
}