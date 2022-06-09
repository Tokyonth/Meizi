package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.panpf.sketch.displayImage

import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.ItemAccurateSearchBinding

class AccurateSearchAdapter : AlbumPictureAdapter<ItemAccurateSearchBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemAccurateSearchBinding {
        return ItemAccurateSearchBinding.inflate(LayoutInflater.from(parent.context))
    }

    @SuppressLint("SetTextI18n")
    override fun convert(
        data: AlbumPictureEntity, holder:
        BaseViewHolder<ItemAccurateSearchBinding>
    ) {
        super.convert(data, holder)
        holder.getItemBinding().tvAccurateSearchAlbumName.text = data.name
        holder.getItemBinding().tvAccurateSearchAlbumCount.text = "${data.nums}P"
        holder.getItemBinding().ivAccurateSearch.displayImage(data.pic)
    }

}
