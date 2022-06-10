package com.tokyonth.mz.ui.fragment

import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.HottestAlbumAdapter
import com.tokyonth.mz.databinding.FragmentHottestBinding
import com.tokyonth.mz.viewmodel.HottestViewModel

class HottestFragment : BaseAlbumFragment() {

    private val binding: FragmentHottestBinding by lazyBind()

    private val model: HottestViewModel by viewModels()

    private val hottestAdapter = HottestAlbumAdapter()

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshHottest

    override fun setRecyclerView() = binding.rvHottestAlbum

    override fun setAdapter() = hottestAdapter

}
