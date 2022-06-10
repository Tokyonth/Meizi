package com.tokyonth.mz.base

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<T, B : ViewBinding> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun itemBind(parent: ViewGroup, viewType: Int): B

    abstract fun convert(data: T, holder: BaseViewHolder<B>)

    private var dataList: MutableList<T> = ArrayList()

    private var placeholderView: ViewBinding? = null

    private var isPlaceholderMode = false

    fun setData(dataList: MutableList<T>) {
        this.dataList = dataList
        isPlaceholderMode = false
        notifyDataSetChanged()
    }

    fun addData(dataList: MutableList<T>) {
        this.dataList.addAll(dataList)
        isPlaceholderMode = false
        notifyDataSetChanged()
    }

    fun getData(): MutableList<T> {
        return dataList
    }

    fun setPlaceholderView(viewBinding: ViewBinding) {
        placeholderView = viewBinding
        dataList.clear()
        isPlaceholderMode = true
        notifyItemChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            BaseViewHolder(itemBind(parent, viewType))
        } else {
            PlaceholderViewHolder(placeholderView!!.root)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder<*>) {
            if (!dataList.isNullOrEmpty()) {
                convert(dataList[position], holder as BaseViewHolder<B>)
            }
        } else {
            (holder as PlaceholderViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
        return if (isPlaceholderMode) {
            1
        } else {
            dataList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPlaceholderMode) {
            1
        } else {
            0
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = RvSpanSize(manager.spanCount)
        }
    }

    inner class RvSpanSize(private val spanSize: Int) : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (isPlaceholderMode) {
                spanSize
            } else {
                1
            }
        }
    }

}

class BaseViewHolder<B : ViewBinding>(private val binding: B) :
    RecyclerView.ViewHolder(binding.root) {

    fun getItemBinding(): B {
        return binding
    }

}

class PlaceholderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind() {
        itemView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

}
