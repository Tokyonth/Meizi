package com.tokyonth.mz.ui.fragment.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.R
import com.tokyonth.mz.adapter.SearchAlbumAdapter
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentSearchAlbumBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.utils.ktx.string
import com.tokyonth.mz.viewmodel.SearchViewModel

class SearchAlbumFragment : BaseFragment() {

    private val binding: FragmentSearchAlbumBinding by lazyBind()

    private val model: SearchViewModel by viewModels()

    private val searchAdapter = SearchAlbumAdapter()

    private var words: String = ""

    override fun setVbRoot() = binding

    override fun initData() {
        words = requireArguments().getString(Constants.INTENT_KEY_SEARCH_WORDS, "")
        model.addParameterData(Pair(Constants.API_SEARCH_MAP_KEY, words))
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.tvSearchAlbumTitle.text = string(R.string.text_search) + ": $words"
        binding.refreshSearch.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.rvSearchAlbum.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
        }
        searchAdapter.setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun initObserve() {
        super.initObserve()
        model.albumLiveData.observe(viewLifecycleOwner) {
            searchAdapter.submitData(it)
        }
        model.refreshLiveData.observe(viewLifecycleOwner) {
            if (it) {
                searchAdapter.clearData()
            }
            binding.refreshSearch.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(viewLifecycleOwner) {
            binding.refreshSearch.finishLoadMore(it)
        }
    }

}
