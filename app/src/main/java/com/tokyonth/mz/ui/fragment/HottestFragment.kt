package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.AlbumPictureAdapter
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.LayoutBaseAlbumPictureBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.viewmodel.AlbumPictureViewModel

class HottestFragment : BaseAlbumFragment<AlbumPictureEntity>() {

    private val binding: LayoutBaseAlbumPictureBinding by lazyBind()

    private val model: AlbumPictureViewModel by viewModels()

    private val hottestAdapter = AlbumPictureAdapter()

    override fun initData() {
        super.initData()
        model.setAlbumType(AlbumPictureViewModel.HOTTEST)
    }

    override fun initView() {
        super.initView()
        hottestAdapter.setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
    }

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshAlbum

    override fun setRecyclerView() = binding.rvAlbumPicture

    override fun setAdapter() = hottestAdapter

}
