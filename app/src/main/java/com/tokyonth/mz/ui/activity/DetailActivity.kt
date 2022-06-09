package com.tokyonth.mz.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.github.panpf.sketch.displayImage
import com.github.panpf.sketch.transform.CircleCropTransformation

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.adapter.DetailPictureAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.data.AccurateEntity
import com.tokyonth.mz.data.DetailAlbumEntity
import com.tokyonth.mz.databinding.ActivityDetailBinding
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.utils.ktx.visibleOrGone
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
        binding.ivAlbumGrid.setOnClickListener {
            AlbumGirdDialog(this)
                .setData(detailAdapter.getData()) {
                    binding.vpDetail.currentItem = it
                }.show()
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
        binding.ivTeamClover.displayImage(detailAlbumEntity.jigouPic) {
            transformations(CircleCropTransformation())
        }
        binding.ivMotelClover.displayImage(detailAlbumEntity.motelPic) {
            transformations(CircleCropTransformation())
        }
        binding.tvMotelName.text = detailAlbumEntity.motelName
        binding.tvTeamName.text = detailAlbumEntity.jigouName
        binding.tvAlbumInfo.text = detailAlbumEntity.name + "\n\n" + detailAlbumEntity.text
    }

    private fun initClick(detailAlbumEntity: DetailAlbumEntity) {
        arrayOf(binding.ivMotelClover, binding.tvMotelName).forEach { v ->
            v.setOnClickListener {
                Intent(this, AccurateSearchActivity::class.java).apply {
                    putExtra(Constants.INTENT_KEY_SEARCH_TYPE, SearchType.MOTEL.name)
                    putExtra(Constants.INTENT_KEY_ALBUM_ID, detailAlbumEntity.motelId)
                    putExtra(
                        Constants.INTENT_KEY_ACCURATE,
                        AccurateEntity(
                            detailAlbumEntity.motelName,
                            detailAlbumEntity.text,
                            detailAlbumEntity.motelPic
                        )
                    )
                }.let {
                    startActivity(it)
                }
            }
        }
        arrayOf(binding.ivTeamClover, binding.tvTeamName).forEach { v ->
            v.setOnClickListener {
                Intent(this, AccurateSearchActivity::class.java).apply {
                    putExtra(Constants.INTENT_KEY_SEARCH_TYPE, SearchType.TEAM.name)
                    putExtra(Constants.INTENT_KEY_ALBUM_ID, detailAlbumEntity.jigouId)
                    putExtra(
                        Constants.INTENT_KEY_ACCURATE,
                        AccurateEntity(
                            detailAlbumEntity.jigouName,
                            detailAlbumEntity.text,
                            detailAlbumEntity.jigouPic
                        )
                    )
                }.let {
                    startActivity(it)
                }
            }
        }
    }

}
