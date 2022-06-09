package com.tokyonth.mz.ui.fragment.search

import android.content.Context
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.base.BaseFragment
import com.tokyonth.mz.databinding.FragmentSearchBinding
import com.tokyonth.mz.ui.activity.SearchActivity
import com.tokyonth.mz.utils.ktx.dp2px
import com.tokyonth.mz.viewmodel.HotTagViewModel
import st235.com.github.flow_layout.FlowLayoutParams

class SearchFragment : BaseFragment() {

    private val binding: FragmentSearchBinding by lazyBind()

    private val model: HotTagViewModel by viewModels()

    private var searchAction: SearchAction? = null

    override fun setVbRoot() = binding

    override fun initData() {
        if (model.hotTagViewModel.value.isNullOrEmpty()) {
            model.getHotTag()
        }
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

    override fun initObserve() {
        super.initObserve()
        model.hotTagViewModel.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                createTag(it)
            }
        }
    }

    private fun createTag(list: List<String>) {
        binding.flowTag.removeAllViews()
        val params = FlowLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(
            4.dp2px().toInt(), 0, 4.dp2px().toInt(), 0
        )
        list.forEach { str ->
            val tag = Chip(requireContext()).apply {
                text = str
                layoutParams = params
                setOnClickListener {
                    searchAction?.onDone(str)
                }
            }
            binding.flowTag.addView(tag)
        }
    }

}
