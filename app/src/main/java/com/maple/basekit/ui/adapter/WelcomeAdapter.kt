package com.maple.basekit.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maple.basekit.R
import com.maple.baselib.ext.layoutInflater

class WelcomeAdapter(val context: Context): RecyclerView.Adapter<WelcomeAdapter.ViewHolder>() {

    private val list: MutableList<Int> = mutableListOf()

    fun setData (l: List<Int>?) {
        if(!l.isNullOrEmpty()) {
            this.list.addAll(l)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder((parent.context?: context).layoutInflater.inflate(R.layout.item_welcome,parent,false))
    }

    override fun getItemCount(): Int = R.layout.item_welcome

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    }
}