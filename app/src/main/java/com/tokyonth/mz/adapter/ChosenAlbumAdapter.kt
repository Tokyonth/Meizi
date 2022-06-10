package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.ItemChosenAlbumBinding
import com.tokyonth.mz.utils.load

class ChosenAlbumAdapter : AlbumPictureAdapter<ItemChosenAlbumBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemChosenAlbumBinding {
        return ItemChosenAlbumBinding.inflate(LayoutInflater.from(parent.context))
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        data: AlbumPictureEntity, holder:
        BaseViewHolder<ItemChosenAlbumBinding>
    ) {
        super.convert(data, holder)
        holder.getItemBinding().tvChosenAlbumName.text = data.name
        holder.getItemBinding().tvChosenAlbumCount.text = "${data.nums}P"
        holder.getItemBinding().ivChosenAlbum.load(data.pic)
    }

}
