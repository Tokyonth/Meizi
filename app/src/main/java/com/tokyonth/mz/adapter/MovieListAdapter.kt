package com.tokyonth.mz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tokyonth.mz.base.BaseViewHolder
import com.tokyonth.mz.data.MovieEntity
import com.tokyonth.mz.databinding.ItemMovieListBinding
import com.tokyonth.mz.utils.load

class MovieListAdapter : BaseListAdapter<MovieEntity, ItemMovieListBinding>() {

    override fun itemBind(parent: ViewGroup, viewType: Int): ItemMovieListBinding {
        return ItemMovieListBinding.inflate(LayoutInflater.from(parent.context))
    }

    override fun convert(data: MovieEntity, holder: BaseViewHolder<ItemMovieListBinding>) {
        super.convert(data, holder)
        holder.getItemBinding().ivMovieClover.load(data.vodPic!!)
        holder.getItemBinding().tvMovieName.text = data.vodTitle
        holder.getItemBinding().tvMovieInfo.text = data.category
    }

}
