package com.tokyonth.mz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.panpf.sketch.displayImage

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.databinding.ItemGridAlbumBinding

class GridAlbumAdapter : BaseAdapter<String, ItemGridAlbumBinding>() {

    private var itemClick: ((Int) -> Unit)? = null

    fun setItemClick(itemClick: (Int) -> Unit) {
        this.itemClick = itemClick
    }

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemGridAlbumBinding {
        return ItemGridAlbumBinding.inflate(LayoutInflater.from(parent.context))
    }

    override fun convert(data: String, holder: BaseViewHolder<ItemGridAlbumBinding>) {
        holder.getItemBinding().ivAlbumGrid.displayImage(data)
        holder.itemView.setOnClickListener {
            itemClick?.invoke(holder.bindingAdapterPosition)
        }
    }

}
