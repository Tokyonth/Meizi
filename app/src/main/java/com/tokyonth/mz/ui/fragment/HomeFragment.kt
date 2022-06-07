package com.tokyonth.mz.ui.fragment

import com.google.android.material.tabs.TabLayoutMediator

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.adapter.FragPagerAdapter
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private val binding: FragmentHomeBinding by lazyBind()

    override fun setVbRoot() = binding

    override fun initData() {

    }

    override fun initView() {
        binding.vpHome.apply {
            adapter = FragPagerAdapter(requireActivity()) {
                add(ChosenFragment())
                add(LatestFragment())
                add(HottestFragment())
            }
        }

        val pagerTitle = arrayOf("精选", "最新", "最热")
        TabLayoutMediator(
            binding.tabAlbum, binding.vpHome
        ) { tab, position ->
            tab.text = pagerTitle[position]
        }.attach()
    }

}
