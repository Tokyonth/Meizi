package com.tokyonth.mz.ui.fragment

import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.AlbumPictureAdapter
import com.tokyonth.mz.databinding.LayoutBaseAlbumPictureBinding
import com.tokyonth.mz.viewmodel.AlbumPictureViewModel

class ChosenFragment : BaseAlbumFragment() {

    private val binding: LayoutBaseAlbumPictureBinding by lazyBind()

    private val model: AlbumPictureViewModel by viewModels()

    private val chosenAdapter = AlbumPictureAdapter()

    override fun initData() {
        super.initData()
        model.setAlbumType(AlbumPictureViewModel.CHOSEN)
    }

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshAlbum

    override fun setRecyclerView() = binding.rvAlbumPicture

    override fun setAdapter() = chosenAdapter

}
