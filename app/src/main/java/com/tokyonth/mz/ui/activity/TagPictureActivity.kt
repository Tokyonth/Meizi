package com.tokyonth.mz.ui.activity

import androidx.activity.viewModels
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

    override fun setVbRoot() = binding

    override fun initData() {
        val index = intent.getIntExtra(Constants.INTENT_KEY_TAG_PICTURE, -1)
        model.setTagPictureType(index)
    }

    override fun initView() {
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
