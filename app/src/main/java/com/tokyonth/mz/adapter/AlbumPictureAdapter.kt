package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import androidx.viewbinding.ViewBinding

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumPictureEntity

@SuppressLint("NotifyDataSetChanged")
abstract class AlbumPictureAdapter<B : ViewBinding> : BaseAdapter<AlbumPictureEntity, B>() {

    private val data = ArrayList<AlbumPictureEntity>()

    private var itemClick: ((AlbumPictureEntity) -> Unit)? = null

    init {
        setData(data)
    }

    fun setItemClick(itemClick: (AlbumPictureEntity) -> Unit) {
        this.itemClick = itemClick
    }

    fun submitData(data: List<AlbumPictureEntity>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun convert(data: AlbumPictureEntity, holder: BaseViewHolder<B>) {
        holder.itemView.setOnClickListener {
            itemClick?.invoke(data)
        }
    }

}
