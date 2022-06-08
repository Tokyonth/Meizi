package com.tokyonth.mz.ui.fragment

import android.content.Intent
import androidx.fragment.app.viewModels

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentDiscoveryBinding
import com.tokyonth.mz.ui.activity.SearchActivity
import com.tokyonth.mz.ui.activity.TagPictureActivity
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.viewmodel.DiscoveryViewModel

class DiscoveryFragment : BaseFragment() {

    private val binding: FragmentDiscoveryBinding by lazyBind()

    private val model: DiscoveryViewModel by viewModels()

    override fun setVbRoot() = binding

    override fun initData() {
        model.getCategoryTag()
        model.getTeamTag()
        model.getMotelTag()
    }

    override fun initView() {
        binding.tvSearch.setOnClickListener {
            Intent(requireContext(), SearchActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_SEARCH_TYPE, SearchType.TEXT.name)
            }.let {
                startActivity(it)
            }
        }
        arrayOf(
            binding.disCategory,
            binding.disMotel,
            binding.disTeam
        ).forEachIndexed { index, view ->
            view.setOnMoreClick {
                val intent = Intent(requireContext(), TagPictureActivity::class.java)
                intent.putExtra(Constants.INTENT_KEY_TAG_PICTURE, index)
                startActivity(intent)
            }
        }
    }

    override fun initObserve() {
        super.initObserve()
        model.categoryTagViewModel.observe(viewLifecycleOwner) {
            binding.disCategory.setData(it)
        }
        model.teamTagViewModel.observe(viewLifecycleOwner) {
            binding.disTeam.setData(it)
        }
        model.motelTagViewModel.observe(viewLifecycleOwner) {
            binding.disMotel.setData(it)
        }
    }

}
