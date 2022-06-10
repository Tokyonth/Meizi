package com.tokyonth.mz.ui.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.AccurateSearchAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.data.AccurateEntity
import com.tokyonth.mz.databinding.ActivityAccurateSearchBinding
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.utils.load
import com.tokyonth.mz.viewmodel.AccurateSearchViewModel

import kotlin.math.abs

class AccurateSearchActivity : BaseActivity() {

    private val binding: ActivityAccurateSearchBinding by lazyBind()

    private val model: AccurateSearchViewModel by viewModels()

    private val accurateSearchAdapter = AccurateSearchAdapter()

    private var accurateEntity: AccurateEntity? = null

    override fun setVbRoot() = binding

    override fun isDarkStatusBars(): Boolean {
        return false
    }

    override fun initData() {
        val searchTypeStr = intent.getStringExtra(Constants.INTENT_KEY_SEARCH_TYPE)
        val searchId = intent.getStringExtra(Constants.INTENT_KEY_ALBUM_ID)
        accurateEntity =
            intent.getSerializableExtra(Constants.INTENT_KEY_ACCURATE) as AccurateEntity?
        model.addParameterData(Pair(Constants.API_ALBUM_ID_MAP_KEY, searchId!!))
        model.setAccurateSearchType(SearchType.valueOf(searchTypeStr!!))
    }

    override fun initView() {
        setToolBar(binding.toolbar, "")
        binding.run {
            ivClover.load(accurateEntity!!.pic, isBlur = true)
            ivAvatar.load(accurateEntity!!.pic, isCircle = true)
            tvToolbar.text = accurateEntity?.name
            tvTitle.text = accurateEntity?.name
            tvInfo.text = accurateEntity?.text
        }
        binding.included.refreshAccurateSearch.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.included.rvAccurateSearch.apply {
            layoutManager = GridLayoutManager(this@AccurateSearchActivity, 2)
            adapter = accurateSearchAdapter
        }
        accurateSearchAdapter.setItemClick {
            Intent(this, DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }
        binding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val i: Int = verticalOffset * 100 / appBarLayout.totalScrollRange
                val num = abs(i.toFloat()) / 100f
                binding.llAccurateInfo.alpha = 1 - num
                binding.tvToolbar.alpha = num
/*                if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {

                } else if (verticalOffset == 0) {

                }*/
            })
    }

    override fun initObserve() {
        super.initObserve()
        model.albumLiveData.observe(this) {
            accurateSearchAdapter.submitData(it)
        }
        model.refreshLiveData.observe(this) {
            if (it) {
                accurateSearchAdapter.clearData()
            }
            binding.included.refreshAccurateSearch.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(this) {
            binding.included.refreshAccurateSearch.finishLoadMore(it)
        }
        model.errorLiveData.observe(this) {
            if (accurateSearchAdapter.itemCount == 0) {
                accurateSearchAdapter.setErrorView(this, it)
            }
        }
    }

}
