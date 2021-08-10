package com.maple.basekit.model.entity
//type 0 url, 1 resId
data class BannerEntity(val name:String, val url:String = "", val resId:Int = 0, val type:Int = 0, val isBanner:Boolean = true)