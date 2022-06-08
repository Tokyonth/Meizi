package com.tokyonth.mz.ui.activity

import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.TagPictureAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.databinding.ActivityTagPictureBinding
import com.tokyonth.mz.viewmodel.TagPictureViewModel

class TagPictureActivity : BaseActivity() {

    private val binding: ActivityTagPictureBinding by lazyBind()

    private val model: TagPictureViewModel by viewModels()

    private val tagAdapter = TagPictureAdapter()

    private var title: String = ""

    override fun setVbRoot() = binding

    override fun initData() {
        val type = intent.getIntExtra(Constants.INTENT_KEY_TAG_PICTURE, -1)
        title = when (type) {
            0 -> "分类"
            1 -> "模特"
            2 -> "机构"
            else -> ""
        }
        model.setTagPictureType(type)
    }

    override fun initView() {
        setToolBar(binding.inToolbar.toolbar, "标签: $title")
        binding.refreshTagPicture.run {
            autoRefresh()
            setOnRefreshListener {
                model.refreshPage()
            }
            setOnLoadMoreListener {
                model.nextPage()
            }
        }
        binding.rvTagPicture.apply {
            layoutManager = GridLayoutManager(this@TagPictureActivity, 4)
            adapter = tagAdapter
        }
/*        chosenAdapter.setItemClick {
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
            }.let {
                startActivity(it)
            }
        }*/
    }

    override fun initObserve() {
        super.initObserve()
        model.pictureLiveData.observe(this) {
            tagAdapter.submitData(it)
        }
        model.refreshLiveData.observe(this) {
            if (it) {
                tagAdapter.clearData()
            }
            binding.refreshTagPicture.finishRefresh(it)
        }
        model.loadMoreLiveData.observe(this) {
            binding.refreshTagPicture.finishLoadMore(it)
        }
    }

}
