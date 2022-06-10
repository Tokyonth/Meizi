package com.tokyonth.mz.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.DetailPictureAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.data.AccurateEntity
import com.tokyonth.mz.data.DetailAlbumEntity
import com.tokyonth.mz.databinding.ActivityDetailBinding
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.utils.ktx.visibleOrGone
import com.tokyonth.mz.utils.load
import com.tokyonth.mz.viewmodel.DetailAlbumViewModel
import com.tokyonth.mz.widget.AlbumGirdDialog

@SuppressLint("SetTextI18n")
class DetailActivity : BaseActivity() {

    private val binding: ActivityDetailBinding by lazyBind()

    private val model: DetailAlbumViewModel by viewModels()

    private val detailAdapter = DetailPictureAdapter()

    override fun setVbRoot() = binding

    override fun isDarkStatusBars(): Boolean {
        return false
    }

    override fun initData() {
        val albumId = intent.getStringExtra(Constants.INTENT_KEY_ALBUM_ID)
        if (albumId != null) {
            model.getDetailAlbum(albumId)
        }
    }

    override fun initView() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.vpDetail.apply {
            adapter = detailAdapter
            offscreenPageLimit = 4
        }
        binding.vpDetail.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvDetailNum.text = "${position + 1} / ${detailAdapter.itemCount}"
            }
        })
        detailAdapter.setItemClick {
            val vis = !binding.csBottom.isVisible
            binding.csBottom.visibleOrGone(vis)
            binding.tvMotelName.visibleOrGone(vis)
            binding.ivMotelClover.visibleOrGone(vis)
        }
    }

    override fun initObserve() {
        super.initObserve()
        model.detailAlbumLiveData.observe(this) {
            display(it)
            initClick(it)
        }
    }

    private fun display(detailAlbumEntity: DetailAlbumEntity) {
        detailAdapter.setData(detailAlbumEntity.data!!)
        binding.ivMotelClover.visibleOrGone(true)
        binding.ivTeamClover.visibleOrGone(true)
        binding.ivTeamClover.load(detailAlbumEntity.jigouPic, isCircle = true)
        binding.ivMotelClover.load(detailAlbumEntity.motelPic, isCircle = true)
        binding.tvMotelName.text = detailAlbumEntity.motelName
        binding.tvTeamName.text = detailAlbumEntity.jigouName
        binding.tvAlbumInfo.text = detailAlbumEntity.name + "\n\n" + detailAlbumEntity.text
    }

    private fun initClick(detailAlbumEntity: DetailAlbumEntity) {
        binding.ivAlbumGrid.setOnClickListener {
            AlbumGirdDialog(this)
                .setData(detailAdapter.getData()) {
                    binding.vpDetail.currentItem = it
                }.show()
        }
        arrayOf(binding.ivMotelClover, binding.tvMotelName).forEach { v ->
            v.setOnClickListener {
                startAccurateSearch(
                    SearchType.MOTEL, detailAlbumEntity.motelId, AccurateEntity(
                        detailAlbumEntity.motelName,
                        detailAlbumEntity.text,
                        detailAlbumEntity.motelPic
                    )
                )
            }
        }
        arrayOf(binding.ivTeamClover, binding.tvTeamName).forEach { v ->
            v.setOnClickListener {
                startAccurateSearch(
                    SearchType.TEAM, detailAlbumEntity.jigouId, AccurateEntity(
                        detailAlbumEntity.jigouName,
                        detailAlbumEntity.text,
                        detailAlbumEntity.jigouPic
                    )
                )
            }
        }
    }

    private fun startAccurateSearch(type: SearchType, id: String, accurate: AccurateEntity) {
        Intent(this, AccurateSearchActivity::class.java).apply {
            putExtra(Constants.INTENT_KEY_SEARCH_TYPE, type.name)
            putExtra(Constants.INTENT_KEY_ALBUM_ID, id)
            putExtra(Constants.INTENT_KEY_ACCURATE, accurate)
        }.let {
            startActivity(it)
        }
    }

}
