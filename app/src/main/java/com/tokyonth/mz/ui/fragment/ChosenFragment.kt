package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.ChosenAlbumAdapter
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentChosenBinding
import com.tokyonth.mz.ui.activity.DetailActivity
import com.tokyonth.mz.viewmodel.ChosenViewModel

class ChosenFragment : BaseFragment() {

    private val binding: FragmentChosenBinding by lazyBind()

    private val model: ChosenViewModel by viewModels()

    private val chosenAdapter = ChosenAlbumAdapter()

    override fun setVbRoot() = binding

    override fun initData() {
        model.getAlbumData()
    }

    override fun initView() {
        binding.refreshChosen.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.rvChosenAlbum.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = chosenAdapter
        }
        chosenAdapter.setItemClick {
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
            chosenAdapter.submitData(it)
        }
        model.refreshLiveData.observe(viewLifecycleOwner) {
            if (it) {
                chosenAdapter.clearData()
            }
            binding.refreshChosen.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(viewLifecycleOwner) {
            binding.refreshChosen.finishLoadMore(it)
        }
    }

}
