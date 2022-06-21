package com.tokyonth.mz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tokyonth.mz.R

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.databinding.ItemGridAlbumBinding
import com.tokyonth.mz.utils.load

class GridAlbumAdapter : BaseAdapter<String, ItemGridAlbumBinding>() {

    private var selectIndex = -1

    private var itemClick: ((Int) -> Unit)? = null

    fun setItemClick(itemClick: (Int) -> Unit) {
        this.itemClick = itemClick
    }

    fun setSelect(position: Int) {
        this.selectIndex = position
        notifyItemChanged(position)
    }

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemGridAlbumBinding {
        return ItemGridAlbumBinding.inflate(LayoutInflater.from(parent.context))
    }

    override fun convert(data: String, holder: BaseViewHolder<ItemGridAlbumBinding>) {
        val index = holder.bindingAdapterPosition + 1
        holder.getItemBinding().ivAlbumGrid.load(data)
        holder.getItemBinding().tvAlbumGridIndex.text = index.toString()
        if (selectIndex == holder.bindingAdapterPosition) {
            holder.getItemBinding().ivAlbumGrid.setBackgroundResource(R.drawable.bg_select_picture)
        } else {
            holder.getItemBinding().ivAlbumGrid.background = null
        }
        holder.itemView.setOnClickListener {
            itemClick?.invoke(holder.bindingAdapterPosition)
        }
    }

}
