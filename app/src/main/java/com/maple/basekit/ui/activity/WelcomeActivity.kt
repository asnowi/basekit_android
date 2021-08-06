package com.maple.basekit.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import com.maple.basekit.R
import com.maple.basekit.app.MyApplication
import com.maple.basekit.app.config.Const
import com.maple.basekit.ui.adapter.WelcomeAdapter
import com.maple.common.base.BaseActivity
import com.maple.common.base.BaseAdapter
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    override fun hasStatusBarMode(): Boolean = true

    override fun setStatusBarMode(color: Int) {
       // super.setStatusBarMode(color)
        UltimateBarX.with(this)
            .transparent()
            .light(true)
            .applyStatusBar()
    }



    private val list by lazy {
        listOf(
            R.drawable.welcome1,
            R.drawable.welcome2,
            R.drawable.welcome3,
            R.drawable.welcome4
        )
    }

    override fun getLayoutId(): Int = R.layout.activity_welcome

    override fun initData(savedInstanceState: Bundle?) {
        vp.adapter = WelcomeAdapter(this).apply {
            this.setData(list)
            this.setListener(object : BaseAdapter.OnClickListener<Int> {
                override fun onItemClick(pos: Int, item: Int?) {
                    if (list.size == (pos + 1)) {
                        launchTarget()
                    }
                }
            })
        }
    }

    private fun launchTarget() {
        SPUtils.getInstance(MyApplication.instance.getAppPackage())
            .put(Const.SaveInfoKey.HAS_APP_FIRST, true)
        onStartActivity(AccountActivity::class.java, isFinish = true)
    }

}