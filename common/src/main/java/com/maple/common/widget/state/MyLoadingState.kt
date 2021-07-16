package com.maple.common.widget.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.maple.common.R
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

class MyLoadingState: MultiState() {

    private var tvLoadingMsg: TextView? = null


    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.my_state_loading, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvLoadingMsg = view.findViewById(R.id.tv_loading_msg)
    }
}