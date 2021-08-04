package com.maple.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.core.graphics.Insets;
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class NavigationBarHelper {

    companion object {
        fun setNavigationBarListener(
            activity: Activity,
            @Nullable listener: NavigationStateListener?
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                val context = activity.applicationContext
                ViewCompat.setOnApplyWindowInsetsListener(activity.window.decorView,
                    object : OnApplyWindowInsetsListener {
                        override fun onApplyWindowInsets(
                            v: View?,
                            insets: WindowInsetsCompat?
                        ): WindowInsetsCompat? {
                            /** 虚拟导航栏或虚拟导航线高度(使用AndroidAutoSize会使该值发生变化)  */
                            val height: Int = getNavigationBarHeight(context)
                            var isShowing = false
                            var temp: Insets? = null
                            if (insets != null) {
                                temp = Insets.of(
                                    insets.systemWindowInsetLeft,
                                    insets.systemWindowInsetTop,
                                    insets.systemWindowInsetRight,
                                    insets.systemWindowInsetBottom
                                )
                                // or
//                            Insets statusInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
//                                temp = insets.getInsets(WindowInsetsCompat.Type.statusBars());
//                            Insets navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars());
                                temp = insets.getInsets(WindowInsetsCompat.Type.navigationBars());
                                isShowing = height - temp.bottom >= 0
                            }
                            listener?.onStateChanged(temp, isShowing)
                            return v?.let { ViewCompat.onApplyWindowInsets(it, insets!!) }
                        }
                    })
            }
        }

        /**
         * 获取虚拟导航栏或虚拟导航线高度
         */
        fun getNavigationBarHeight(@NonNull context: Context): Int {
            val resources: Resources = context.getApplicationContext().getResources()
            val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return resources.getDimensionPixelSize(resourceId)
        }
    }


    interface NavigationStateListener {
        /**
         *
         * @param insets
         * @param isNavShowing 是否显示虚拟导航栏或虚拟导航线
         */
        fun onStateChanged(insets: Insets?, isNavShowing: Boolean)
    }
}