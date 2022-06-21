package com.tokyonth.mz.ui.fragment

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentMovieBinding

class MovieFragment : BaseFragment() {

    private val binding: FragmentMovieBinding by lazyBind()

    override fun setVbRoot() = binding

    override fun initData() {

    }

    override fun initView() {

    }

}
