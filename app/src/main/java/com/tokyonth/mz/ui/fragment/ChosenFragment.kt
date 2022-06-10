package com.tokyonth.mz.ui.fragment

import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.ChosenAlbumAdapter
import com.tokyonth.mz.databinding.FragmentChosenBinding
import com.tokyonth.mz.viewmodel.ChosenViewModel

class ChosenFragment : BaseAlbumFragment() {

    private val binding: FragmentChosenBinding by lazyBind()

    private val model: ChosenViewModel by viewModels()

    private val chosenAdapter = ChosenAlbumAdapter()

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshChosen

    override fun setRecyclerView() = binding.rvChosenAlbum

    override fun setAdapter() = chosenAdapter

}
