package com.maple.basekit.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.maple.basekit.R
import com.maple.basekit.app.MyApplication
import com.maple.basekit.app.config.Const
import com.maple.baselib.utils.PermissionUtil
import com.maple.baselib.utils.RequestPermission
import com.maple.common.base.BaseActivity
import com.maple.common.widget.dialog.CommonDialog
import com.maple.common.widget.dialog.UsedDialog
import com.tbruyelle.rxpermissions2.RxPermissions

class SplashActivity : BaseActivity() {

    private val hasFirst by lazy {
        SPUtils.getInstance(MyApplication.instance.packageName).getBoolean(Const.SaveInfoKey.HAS_APP_FIRST, false)
    }

    private var commonDialog: CommonDialog? = null

    private val usedDialog by lazy {
        UsedDialog(this, false, object : UsedDialog.OnClickListener {
            override fun onCancelClick() {
                onFinish()
            }

            override fun onConfirmClick() {
                applyPermissions()
            }

            override fun onPolicyClick() {
                showToast("隐私政策")
            }

            override fun onAgreementClick() {
                showToast("用户协议")
            }
        })
    }


    private val rxPermissions: RxPermissions by lazy { RxPermissions(this) }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {

        if (hasFirst) {
            applyPermissions()
        } else {
            showUsedDialog()
        }
    }


    override fun onRestart() {
        super.onRestart()
        applyPermissions()
    }

    private fun applyPermissions() {
        PermissionUtil.applyPermissions(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                launchTarget()
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                showLoading()
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                showLoading()
            }
        }, rxPermissions)
    }

    private fun showUsedDialog() {
        usedDialog.show()
    }

    private fun launchTarget() {
        if (hasFirst) {
            onStartActivity(AccountActivity::class.java, isFinish = true)
        } else {
            onStartActivity(WelcomeActivity::class.java, isFinish = true)
        }
    }

    override fun showLoading() {
        // super.showLoading()
        commonDialog ?: CommonDialog(this,
            "权限申请",
            "请开启必要权限，否则您无法正常使用一些功能",
            "不同意",
            "前往开启", object : CommonDialog.OnClickListener {
                override fun onCancelClick() {
                    onFinish()
                }

                override fun onConfirmClick() {
                    AppUtils.launchAppDetailsSettings()
                }
            }).let {
            if (!it.isShowing) it.show()
        }
    }

    override fun dismissLoading() {
       // super.dismissLoading()
        commonDialog?.cancel()
    }
}