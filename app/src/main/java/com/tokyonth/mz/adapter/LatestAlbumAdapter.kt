package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import com.github.panpf.sketch.displayImage

import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.ItemLatestAlbumBinding

class LatestAlbumAdapter : AlbumPictureAdapter<ItemLatestAlbumBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemLatestAlbumBinding {
        return ItemLatestAlbumBinding.inflate(LayoutInflater.from(parent.context))
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        data: AlbumPictureEntity, holder:
        BaseViewHolder<ItemLatestAlbumBinding>
    ) {
        super.convert(data, holder)
        holder.getItemBinding().tvLatestAlbumName.text = data.name
        holder.getItemBinding().tvChosenAlbumCount.text = "${data.nums}P"
        holder.getItemBinding().ivLatestAlbum.displayImage(data.pic)
    }

}
