package com.tokyonth.mz.ui.fragment

import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.LatestAlbumAdapter
import com.tokyonth.mz.databinding.FragmentLatestBinding
import com.tokyonth.mz.viewmodel.LatestViewModel

class LatestFragment : BaseAlbumFragment() {

    private val binding: FragmentLatestBinding by lazyBind()

    private val model: LatestViewModel by viewModels()

    private val latestAdapter = LatestAlbumAdapter()

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshLatest

    override fun setRecyclerView() = binding.rvLatestAlbum

    override fun setAdapter() = latestAdapter

}
