package com.tokyonth.mz.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.tokyonth.mz.base.BaseFragment

class FragPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    fragArray: ArrayList<BaseFragment>.() -> Unit
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentPages = ArrayList<BaseFragment>()

    init {
        fragArray.invoke(fragmentPages)
    }

    override fun getItemCount(): Int {
        return fragmentPages.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentPages[position]
    }

}
