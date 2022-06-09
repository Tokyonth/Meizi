package com.tokyonth.mz.ui.activity

import android.os.Bundle

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.databinding.ActivitySearchBinding
import com.tokyonth.mz.ui.fragment.search.SearchAction
import com.tokyonth.mz.ui.fragment.search.SearchAlbumFragment
import com.tokyonth.mz.ui.fragment.search.SearchFragment

class SearchActivity : BaseActivity(), SearchAction {

    private val binding: ActivitySearchBinding by lazyBind()

    private var searchName: String? = null

    private var searchId: String? = null

    override fun setVbRoot() = binding

    override fun initData() {
        searchName = intent.getStringExtra(Constants.INTENT_KEY_SEARCH_WORDS)
        searchId = intent.getStringExtra(Constants.INTENT_KEY_ALBUM_ID)
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragContainer.id, SearchFragment())
            .commit()
    }

    override fun onDone(words: String) {
        if (words.isNotEmpty()) {
            val searchFragment = SearchAlbumFragment()
            searchFragment.arguments = Bundle().apply {
                putString(Constants.INTENT_KEY_SEARCH_WORDS, words)
            }
            supportFragmentManager.beginTransaction()
                .replace(binding.fragContainer.id, searchFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}
