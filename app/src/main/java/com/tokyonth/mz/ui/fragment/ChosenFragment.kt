package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.AlbumPictureAdapter
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.LayoutBaseListBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.viewmodel.AlbumPictureViewModel

class ChosenFragment : BaseListFragment<AlbumPictureEntity>() {

    private val binding: LayoutBaseListBinding by lazyBind()

    private val model: AlbumPictureViewModel by viewModels()

    private val chosenAdapter = AlbumPictureAdapter()

    override fun initData() {
        super.initData()
        model.setAlbumType(AlbumPictureViewModel.CHOSEN)
    }

    override fun initView() {
        super.initView()
        chosenAdapter.setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
    }

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.refreshList

    override fun setRecyclerView() = binding.rvList

    override fun setAdapter() = chosenAdapter

}
