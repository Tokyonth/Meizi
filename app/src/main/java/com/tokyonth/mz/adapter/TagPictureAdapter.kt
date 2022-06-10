package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.databinding.ItemTagPictureBinding
import com.tokyonth.mz.databinding.LayoutRvPlaceholderBinding
import com.tokyonth.mz.utils.load

@SuppressLint("NotifyDataSetChanged")
class TagPictureAdapter : BaseAdapter<AlbumTagEntity, ItemTagPictureBinding>() {

    private var itemClick: ((AlbumTagEntity) -> Unit)? = null

    fun setItemClick(itemClick: (AlbumTagEntity) -> Unit) {
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

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemTagPictureBinding {
        return ItemTagPictureBinding.inflate(LayoutInflater.from(parent.context))
    }

    override fun convert(data: AlbumTagEntity, holder: BaseViewHolder<ItemTagPictureBinding>) {
        holder.getItemBinding().ivTagPicture.load(data.pic, true)
        holder.getItemBinding().tvTagPicture.text = data.name
        holder.itemView.setOnClickListener {
            itemClick?.invoke(data)
        }
    }

}
