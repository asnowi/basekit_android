package com.maple.basekit.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.maple.basekit.R
import com.maple.basekit.model.entity.BannerEntity
import com.maple.baselib.utils.UIUtils
import com.maple.common.ext.loadImage
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter: BaseBannerAdapter<BannerEntity>(){
    override fun bindData(
        holder: BaseViewHolder<BannerEntity>?,
        data: BannerEntity?,
        position: Int,
        pageSize: Int
    ) {
        data?.let { d ->
            holder?.apply {
                findViewById<ImageView>(R.id.banner_image)?.loadImage(if(d.type == 1) UIUtils.getDrawable(d.resId) else d.url)
                findViewById<TextView>(R.id.tv_describe)?.text = d.name
                findViewById<TextView>(R.id.tv_type)?.text = "是"
            }
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }
}