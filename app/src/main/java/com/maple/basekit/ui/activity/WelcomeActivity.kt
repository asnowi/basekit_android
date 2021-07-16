package com.maple.basekit.ui.activity

import android.os.Bundle
import com.maple.basekit.R
import com.maple.basekit.ui.adapter.WelcomeAdapter
import com.maple.common.base.BaseActivity

class WelcomeActivity : BaseActivity() {

    private val list by lazy {
        listOf(
            R.drawable.welcome1,
            R.drawable.welcome2,
            R.drawable.welcome3,
            R.drawable.welcome4
        )
    }

    private val adapter by lazy { WelcomeAdapter(this) }

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initData(savedInstanceState: Bundle?) {
//        vp.adapter = WelcomeAdapter(this).apply {
//            this.setData(list)
//        }

    }

}