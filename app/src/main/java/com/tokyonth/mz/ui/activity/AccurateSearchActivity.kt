package com.tokyonth.mz.ui.activity

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.github.panpf.sketch.displayImage
import com.github.panpf.sketch.transform.BlurTransformation
import com.github.panpf.sketch.transform.CircleCropTransformation
import com.google.android.material.appbar.AppBarLayout
import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.AccurateSearchAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.data.AccurateEntity
import com.tokyonth.mz.databinding.ActivityAccurateSearchBinding
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.viewmodel.AccurateSearchViewModel
import kotlin.math.abs


class AccurateSearchActivity : BaseActivity() {

    private val binding: ActivityAccurateSearchBinding by lazyBind()

    private val model: AccurateSearchViewModel by viewModels()

    private val accurateSearchAdapter = AccurateSearchAdapter()

    private var accurateEntity: AccurateEntity? = null


    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
    private val ALPHA_ANIMATIONS_DURATION = 200L

    private var mIsTheTitleVisible = false
    private var mIsTheTitleContainerVisible = true

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
      //  WindowCompat.setDecorFitsSystemWindows(window, true)
       // setToolBar(binding.toolbar, "")
        binding.tvTitle.text = accurateEntity!!.name
        binding.tvAccurateTitle.text = accurateEntity!!.name
        binding.tvAccurateInfo.text = accurateEntity!!.text
        binding.ivAvatar.displayImage(accurateEntity!!.pic) {
            transformations(CircleCropTransformation())
        }
        binding.ivHeadBg.displayImage(accurateEntity!!.pic) {
            transformations(BlurTransformation(maskColor = Color.parseColor("#80000000")))
        }

        binding.refreshAccurateSearch.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.rvAccurateSearch.apply {
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
/*        binding.appBar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                    binding.toolbar.navigationIcon?.setTint(Color.parseColor("#444444"))
                } else if (verticalOffset == 0) {
                    binding.toolbar.navigationIcon?.setTint(Color.WHITE)
                }
            })*/
        startAlphaAnimation(binding.tvTitle, 0, View.INVISIBLE);
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
            binding.refreshAccurateSearch.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(this) {
            binding.refreshAccurateSearch.finishLoadMore(it)
        }
        binding.appbar.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val maxScroll = appBarLayout.totalScrollRange
                val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()

                handleAlphaOnTitle(percentage)
                handleToolbarTitleVisibility(percentage)
            })
    }

    private fun handleToolbarTitleVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(binding.tvTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                mIsTheTitleVisible = true

            }
            binding.toolbar.visibility = View.VISIBLE
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(binding.tvTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                mIsTheTitleVisible = false

            }
            binding.toolbar.visibility = View.INVISIBLE
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.llTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                mIsTheTitleContainerVisible = false
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(binding.llTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                mIsTheTitleContainerVisible = true
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation =
            if (visibility == View.VISIBLE) AlphaAnimation(0f, 1f) else AlphaAnimation(1f, 0f)
        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

}
