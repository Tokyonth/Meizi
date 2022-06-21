package com.tokyonth.mz.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.R
import com.tokyonth.mz.adapter.TagPictureAdapter
import com.tokyonth.mz.data.AccurateEntity
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.databinding.ActivityTagPictureBinding
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.utils.ktx.string
import com.tokyonth.mz.viewmodel.TagPictureViewModel

class TagPictureActivity : BaseListActivity<AlbumTagEntity>() {

    private val binding: ActivityTagPictureBinding by lazyBind()

    private val model: TagPictureViewModel by viewModels()

    private val tagAdapter = TagPictureAdapter()

    private var searchType: SearchType? = null

    private var title: String = ""

    override fun setVbRoot() = binding

    override fun setAlbumModel() = model

    override fun setRefreshView() = binding.included.refreshAlbum

    override fun setRecyclerView() = binding.included.rvAlbumPicture

    override fun setAdapter() = tagAdapter

    override fun initData() {
        super.initData()
        val type = intent.getIntExtra(Constants.INTENT_KEY_TAG_PICTURE, -1)
        val (title, searchType) = when (type) {
            0 -> Pair(string(R.string.label_category), SearchType.CATEGORY)
            1 -> Pair(string(R.string.label_motel), SearchType.MOTEL)
            2 -> Pair(string(R.string.label_team), SearchType.TEAM)
            else -> Pair(string(R.string.label_category), SearchType.CATEGORY)
        }
        this.title = title
        this.searchType = searchType
        model.setTagPictureType(searchType)
    }

    override fun initView() {
        super.initView()
        setToolBar(binding.inToolbar.toolbar, title)
        binding.inToolbar.toolbar.navigationIcon?.setTint(Color.parseColor("#444444"))
        binding.included.rvAlbumPicture.apply {
            layoutManager = GridLayoutManager(this@TagPictureActivity, 4)
            adapter = tagAdapter
        }
        tagAdapter.setItemClick {
            Intent(this, AccurateSearchActivity::class.java).apply {
                putExtra(Constants.INTENT_KEY_SEARCH_TYPE, searchType!!.name)
                putExtra(Constants.INTENT_KEY_ALBUM_ID, it.id)
                putExtra(Constants.INTENT_KEY_ACCURATE, AccurateEntity(it.name, it.text, it.pic))
            }.let {
                startActivity(it)
            }
        }
    }

}
