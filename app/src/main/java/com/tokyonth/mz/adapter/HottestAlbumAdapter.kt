package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.ItemHottestAlbumBinding
import com.tokyonth.mz.utils.load

class HottestAlbumAdapter : AlbumPictureAdapter<ItemHottestAlbumBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemHottestAlbumBinding {
        return ItemHottestAlbumBinding.inflate(LayoutInflater.from(parent.context))
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        data: AlbumPictureEntity,
        holder: BaseViewHolder<ItemHottestAlbumBinding>
    ) {
        super.convert(data, holder)
        holder.getItemBinding().tvHottestAlbumName.text = data.name
        holder.getItemBinding().tvChosenAlbumCount.text = "${data.nums}P"
        holder.getItemBinding().ivHottestAlbum.load(data.pic)
    }

}
