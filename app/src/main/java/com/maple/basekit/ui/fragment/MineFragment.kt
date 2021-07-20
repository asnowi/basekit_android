package com.maple.basekit.ui.fragment

import androidx.fragment.app.Fragment
import com.maple.basekit.vm.HomeViewModel

class MineFragment(val viewModel: HomeViewModel) : Fragment() {

    companion object {
        @JvmStatic
        fun getInstance(viewModel: HomeViewModel): MineFragment {
            return MineFragment(viewModel)
        }
    }
}