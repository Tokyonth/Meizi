package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup

import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.ItemAlbumPictureBinding
import com.tokyonth.mz.utils.load

class AlbumPictureAdapter : BaseListAdapter<AlbumPictureEntity, ItemAlbumPictureBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemAlbumPictureBinding {
        return ItemAlbumPictureBinding.inflate(LayoutInflater.from(parent.context))
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        data: AlbumPictureEntity,
        holder: BaseViewHolder<ItemAlbumPictureBinding>
    ) {
        super.convert(data, holder)
        holder.getItemBinding().tvPictureAlbumName.text = data.name
        holder.getItemBinding().tvPictureAlbumCount.text = "${data.nums}P"
        holder.getItemBinding().ivAlbumPicture.load(data.pic)
    }

}
