package com.tokyonth.mz.ui.fragment.search

import android.content.Context
import android.view.KeyEvent

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentSearchBinding
import com.tokyonth.mz.ui.activity.SearchActivity

class SearchFragment : BaseFragment() {

    private val binding: FragmentSearchBinding by lazyBind()

    private var searchAction: SearchAction? = null

    override fun setVbRoot() = binding

    override fun initData() {

    }

    override fun initView() {
        binding.etSearch.requestFocus()
        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event?.action == KeyEvent.ACTION_UP) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchAction?.onDone(binding.etSearch.text.toString())
                }
            }
            false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchActivity) {
            searchAction = context
        }
    }

}
