package com.maple.basekit.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maple.basekit.R
import com.maple.basekit.model.entity.BannerEntity
import com.maple.basekit.model.entity.MainEntity
import com.maple.baselib.app.BaseApp
import com.maple.baselib.ext.layoutInflater
import com.maple.baselib.utils.UIUtils
import com.maple.baselib.widget.imageloader.ImageConfig
import com.maple.baselib.widget.imageloader.ImageLoader
import com.maple.baselib.widget.imageloader.TransType
import com.maple.baselib.widget.imageloader.glide.GlideImageConfig
import com.maple.common.ext.loadConfigImage
import com.maple.common.ext.loadImage
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder


class MainAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var bannerList: List<BannerEntity>? = null
    private var menuList: List<MainEntity>? = null

    private var listener: OnClickListener? = null

    companion object {
        //轮播图
        const val TYPE_BANNER: Int = 1
        //列表
        const val TYPE_LIST: Int = 2
    }

    fun setListener(listener: OnClickListener?) {
        this.listener = listener
    }


    fun setBannerData(list: List<BannerEntity>?) {
        this.bannerList = list
        notifyItemChanged(0)
    }


    fun setListData(list: List<MainEntity>?) {
        this.menuList = list
        notifyItemChanged(1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_BANNER) {
            val bannerView: View = context.layoutInflater.inflate(R.layout.banner,parent,false)
            return BannerViewHolder(bannerView)
        }
        val listView: View = context.layoutInflater.inflate(R.layout.item_main,parent,false)
        return ListViewHolder(listView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if ((holder is BannerViewHolder)) {
            holder.setData(position)
        } else if ((holder is ListViewHolder)){
            holder.setData(position)
        }
    }

    override fun getItemCount(): Int {
        return if(menuList.isNullOrEmpty()) 1 else menuList!!.size + 1
    }


    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return TYPE_BANNER
        }
        return TYPE_LIST
    }


    inner class BannerViewHolder(itemView: View) : BaseViewHolder<BannerEntity>(itemView) {
        fun setData(pos: Int) {
            val bannerViewPager: BannerViewPager<BannerEntity>? = itemView.findViewById(R.id.banner)
            bannerViewPager?.apply {
                adapter = BannerAdapter()
                setOnPageClickListener { clickedView, position ->
                    listener?.onBannerItemClick(position, bannerList?.get(position))
                }
            }?.create(bannerList)
        }
    }



    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val root: LinearLayout? = itemView.findViewById(R.id.root_list)
        private val tvName: TextView? = itemView.findViewById(R.id.tv_name)
        private val ivImg: ImageView? = itemView.findViewById(R.id.iv_img)
        fun setData(pos: Int) {
                menuList?.let { list ->
                    tvName?.text = list.get(pos - 1).name
                    ivImg?.loadConfigImage(if(list.get(pos - 1).type == 1) UIUtils.getDrawable(list.get(pos - 1).resId) else list.get(pos - 1).url,config = GlideImageConfig(
                            if(list.get(pos - 1).type == 1) UIUtils.getDrawable(list.get(pos - 1).resId) else list.get(pos - 1).url,
                            ivImg
                    ).apply { type = TransType.CIRCLE_ANR_BLUR })
                    root?.setOnClickListener {
                        listener?.onListItemClick(pos, list.get(pos - 1))
                    }
                }
            }
    }

    interface OnClickListener {
        fun onBannerItemClick(pos: Int, item: BannerEntity?)
        fun onListItemClick(pos: Int, item: MainEntity?)
    }
}
