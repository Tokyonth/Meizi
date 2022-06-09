package com.tokyonth.mz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.panpf.sketch.displayImage

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.databinding.ItemDetailPictureBinding

class DetailPictureAdapter : BaseAdapter<String, ItemDetailPictureBinding>() {

    private var itemClick: ((Int) -> Unit)? = null

    fun setItemClick(itemClick: (Int) -> Unit) {
        this.itemClick = itemClick
    }

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemDetailPictureBinding {
        return ItemDetailPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun convert(data: String, holder: BaseViewHolder<ItemDetailPictureBinding>) {
        holder.getItemBinding().ivDetailPicture.displayImage(data)
        holder.getItemBinding().ivDetailPicture.setOnClickListener {
            itemClick?.invoke(holder.bindingAdapterPosition)
        }
    }

}
