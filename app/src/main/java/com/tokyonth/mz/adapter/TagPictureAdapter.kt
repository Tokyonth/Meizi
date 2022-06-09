package com.tokyonth.mz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.panpf.sketch.displayImage
import com.github.panpf.sketch.transform.CircleCropTransformation

import com.tokyonth.mz.base.BaseAdapter
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.databinding.ItemTagPictureBinding

@SuppressLint("NotifyDataSetChanged")
class TagPictureAdapter : BaseAdapter<AlbumTagEntity, ItemTagPictureBinding>() {

    private val data = ArrayList<AlbumTagEntity>()

    private var itemClick: ((AlbumTagEntity) -> Unit)? = null

    init {
        setData(data)
    }

    fun setItemClick(itemClick: (AlbumTagEntity) -> Unit) {
        this.itemClick = itemClick
    }

    fun submitData(data: List<AlbumTagEntity>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemTagPictureBinding {
        return ItemTagPictureBinding.inflate(LayoutInflater.from(parent.context))
    }

    override fun convert(data: AlbumTagEntity, holder: BaseViewHolder<ItemTagPictureBinding>) {
        holder.getItemBinding().ivTagPicture.displayImage(data.pic) {
            transformations(CircleCropTransformation())
        }
        holder.getItemBinding().tvTagPicture.text = data.name
        holder.itemView.setOnClickListener {
            itemClick?.invoke(data)
        }
    }

}
