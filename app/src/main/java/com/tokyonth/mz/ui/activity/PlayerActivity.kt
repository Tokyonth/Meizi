package com.tokyonth.mz.ui.activity

import com.shuyu.gsyvideoplayer.cache.CacheFactory
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.databinding.ActivityVideoPlayerBinding
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager
import tv.danmaku.ijk.media.exo2.ExoPlayerCacheManager

class PlayerActivity : BaseActivity() {

    private val binding: ActivityVideoPlayerBinding by lazyBind()

    override fun setVbRoot() = binding

    override fun initData() {
        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        CacheFactory.setCacheManager(ExoPlayerCacheManager::class.java)

        val url = intent.getStringExtra(Constants.INTENT_KEY_VIDEO_URL)
        val title = intent.getStringExtra(Constants.INTENT_KEY_VIDEO_TITLE)
        binding.playerView.setUp(url, true, title)
    }

    override fun initView() {

    }

}
