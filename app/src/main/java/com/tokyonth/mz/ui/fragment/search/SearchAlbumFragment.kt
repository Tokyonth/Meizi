package com.tokyonth.mz.ui.fragment.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.R
import com.tokyonth.mz.adapter.AlbumPictureAdapter
import com.tokyonth.mz.data.AlbumPictureEntity
import com.tokyonth.mz.databinding.FragmentSearchAlbumBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.ui.fragment.BaseListFragment
import com.tokyonth.mz.utils.ktx.string
import com.tokyonth.mz.viewmodel.AlbumPictureViewModel

class SearchAlbumFragment : BaseListFragment<AlbumPictureEntity>() {

    private val binding: FragmentSearchAlbumBinding by lazyBind()

    private val model: AlbumPictureViewModel by viewModels()

    private val searchAdapter = AlbumPictureAdapter()

    private var words: String = ""

    override fun setVbRoot() = binding

    override fun initData() {
        super.initData()
        words = requireArguments().getString(Constants.INTENT_KEY_SEARCH_WORDS, "")
        model.run {
            setAlbumType(AlbumPictureViewModel.SEARCH)
            addParameterData(Pair(Constants.API_SEARCH_MAP_KEY, words))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        super.initView()
        binding.tvSearchAlbumTitle.text = string(R.string.text_search) + ": $words"
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        searchAdapter.setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
    }

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.included.refreshList

    override fun setRecyclerView() = binding.included.rvList

    override fun setAdapter() = searchAdapter

}
