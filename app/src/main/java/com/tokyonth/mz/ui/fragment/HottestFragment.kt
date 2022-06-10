package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.HottestAlbumAdapter
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentHottestBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.viewmodel.HottestViewModel

class HottestFragment : BaseFragment() {

    private val binding: FragmentHottestBinding by lazyBind()

    private val model: HottestViewModel by viewModels()

    private val hottestAdapter = HottestAlbumAdapter()

    override fun setVbRoot() = binding

    override fun initData() {

    }

    override fun initView() {
        binding.refreshHottest.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.rvHottestAlbum.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = hottestAdapter
        }
        hottestAdapter.setItemClick {
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
            hottestAdapter.submitData(it)
        }
        model.refreshLiveData.observe(viewLifecycleOwner) {
            if (it) {
                hottestAdapter.clearData()
            }
            binding.refreshHottest.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(viewLifecycleOwner) {
            binding.refreshHottest.finishLoadMore(it)
        }
    }

}
