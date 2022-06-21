package com.tokyonth.mz.ui.fragment

import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.AlbumPictureAdapter
import com.tokyonth.mz.databinding.LayoutBaseAlbumPictureBinding
import com.tokyonth.mz.viewmodel.AlbumPictureViewModel

class LatestFragment : BaseAlbumFragment() {

    private val binding: LayoutBaseAlbumPictureBinding by lazyBind()

    private val model: AlbumPictureViewModel by viewModels()

    private val latestAdapter = AlbumPictureAdapter()

    override fun initData() {
        super.initData()
        model.setAlbumType(AlbumPictureViewModel.LATEST)
    }

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshAlbum

    override fun setRecyclerView() = binding.rvAlbumPicture

    override fun setAdapter() = latestAdapter

}
