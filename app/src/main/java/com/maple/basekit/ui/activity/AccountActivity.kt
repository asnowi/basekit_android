package com.maple.basekit.ui.activity

import android.os.Bundle
import android.transition.Slide
import androidx.navigation.fragment.findNavController
import com.maple.basekit.R
import com.maple.common.base.BaseActivity

class AccountActivity : BaseActivity() {

    override fun setContentLayout() {
        super.setContentLayout()
    }

    override fun getLayoutId(): Int = R.layout.activity_account

    override fun onBeforeCreate(savedInstanceState: Bundle?) {
        super.onBeforeCreate(savedInstanceState)
        window.enterTransition = Slide().apply { duration = 100L }
        window.exitTransition = Slide().apply { duration = 50L }
    }

    override fun initData(savedInstanceState: Bundle?) {
//        val navController = Navigation.findNavController(this, R.id.fragment)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment)
        val navController = navHost?.findNavController()

        navController?.setGraph(R.navigation.navigation_account)
        navController?.navigateUp()
    }

}