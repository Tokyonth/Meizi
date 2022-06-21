package com.tokyonth.mz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.databinding.ItemTagPictureBinding
import com.tokyonth.mz.utils.load

class TagPictureAdapter : BaseListAdapter<AlbumTagEntity, ItemTagPictureBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemTagPictureBinding {
        return ItemTagPictureBinding.inflate(LayoutInflater.from(parent.context))
    }

    override fun convert(data: AlbumTagEntity, holder: BaseViewHolder<ItemTagPictureBinding>) {
        super.convert(data, holder)
        holder.getItemBinding().ivTagPicture.load(data.pic, true)
        holder.getItemBinding().tvTagPicture.text = data.name
    }

}
