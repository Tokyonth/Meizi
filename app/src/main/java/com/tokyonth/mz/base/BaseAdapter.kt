package com.tokyonth.mz.base

import android.annotation.SuppressLint
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, B : ViewBinding> :
    RecyclerView.Adapter<BaseViewHolder<B>>() {

    abstract fun itemBind(parent: ViewGroup, viewType: Int): B

    abstract fun convert(data: T, holder: BaseViewHolder<B>)

    private var dataList: List<T>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataList: List<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun getData(): List<T> {
        return dataList!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B> {
        val binding = itemBind(parent, viewType)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) {
        if (!dataList.isNullOrEmpty()) {
            convert(dataList!![position], holder)
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

}

class BaseViewHolder<B : ViewBinding>(private val binding: B) :
    RecyclerView.ViewHolder(binding.root) {

    fun getItemBinding(): B {
        return binding
    }

}
