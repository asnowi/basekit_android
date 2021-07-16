package com.maple.basekit.ui.activity

import android.os.Bundle
import com.maple.basekit.R
import com.maple.common.base.BaseActivity

class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initData(savedInstanceState: Bundle?) {

        onStartActivity(WelcomeActivity::class.java, isFinish = true)
    }
}