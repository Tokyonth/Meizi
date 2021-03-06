package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.databinding.LayoutRvPlaceholderBinding

@SuppressLint("NotifyDataSetChanged")
abstract class BaseListAdapter<T, B : ViewBinding> : BaseAdapter<T, B>() {

    private var itemClick: ((T) -> Unit)? = null

    fun setItemClick(itemClick: (T) -> Unit) {
        this.itemClick = itemClick
    }

    fun clearData() {
        getData().clear()
        notifyDataSetChanged()
    }

    fun setErrorView(context: Context, info: String) {
        LayoutRvPlaceholderBinding.inflate(LayoutInflater.from(context)).let {
            it.tvErrorInfo.text = info
            setPlaceholderView(it)
        }
    }

    override fun getItemCount(): Int {
        return if (getData().isEmpty() && !isPlaceholderMode()) {
            10
        } else {
            super.getItemCount()
        }
    }

    override fun convert(data: T, holder: BaseViewHolder<B>) {
        holder.itemView.setOnClickListener {
            itemClick?.invoke(data)
        }
    }

}
