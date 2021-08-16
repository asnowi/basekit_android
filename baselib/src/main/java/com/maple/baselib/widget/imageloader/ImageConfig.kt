package com.maple.baselib.widget.imageloader

import android.widget.ImageView
import com.facebook.shimmer.ShimmerDrawable

open class ImageConfig {
    lateinit var any:Any
    lateinit var imageView: ImageView
    var placeholder:Int = 0
    var errorPic:Int = 0
    var loadingDrawable: ShimmerDrawable? = null
}