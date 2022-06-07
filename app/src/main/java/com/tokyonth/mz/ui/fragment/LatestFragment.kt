package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.LatestAlbumAdapter
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentLatestBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.viewmodel.LatestViewModel

class LatestFragment : BaseFragment() {

    private val binding: FragmentLatestBinding by lazyBind()

    private val model: LatestViewModel by viewModels()

    private val latestAdapter = LatestAlbumAdapter()

    override fun setVbRoot() = binding

    override fun initData() {
        model.getAlbumData()
    }

    override fun initView() {
        binding.refreshLatest.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.rvLatestAlbum.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = latestAdapter
        }
        latestAdapter.setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
    }

    override fun initObserve() {
        super.initObserve()
        model.albumLiveData.observe(viewLifecycleOwner) {
            latestAdapter.submitData(it)
        }
        model.refreshLiveData.observe(viewLifecycleOwner) {
            if (it) {
                latestAdapter.clearData()
            }
            binding.refreshLatest.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(viewLifecycleOwner) {
            binding.refreshLatest.finishLoadMore(it)
        }
    }

}
