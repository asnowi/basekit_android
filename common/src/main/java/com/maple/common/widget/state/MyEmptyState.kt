package com.maple.common.widget.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.maple.common.R
import com.zy.multistatepage.MultiState
import com.zy.multistatepage.MultiStateContainer

class MyEmptyState: MultiState() {

    private var tvEmptyMsg: TextView? = null
    private var imgEmpty: ImageView? = null

    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.my_state_empty, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvEmptyMsg = view.findViewById(R.id.tv_empty_msg)
        imgEmpty = view.findViewById(R.id.img_empty)
    }
}