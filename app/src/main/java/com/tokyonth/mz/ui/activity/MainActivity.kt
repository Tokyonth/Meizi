package com.tokyonth.mz.ui.activity

import android.content.Intent
import android.os.Bundle

import com.tokyonth.bt.utils.ktx.lazyBind
import com.tokyonth.mz.Constants
import com.tokyonth.mz.R
import com.tokyonth.mz.adapter.FragPagerAdapter
import com.tokyonth.mz.base.BaseActivity
import com.tokyonth.mz.databinding.ActivityMainBinding
import com.tokyonth.mz.ui.fragment.*
import com.tokyonth.mz.utils.SPUtils.getSP

class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazyBind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getBooleanExtra(Constants.INTENT_KEY_VERIFIED, false)) {
            return
        }
        if (getSP(Constants.SP_NEED_PASSWORD, false)) {
            startActivity(Intent(this, PasswordActivity::class.java))
            finish()
            return
        }
    }

    override fun setVbRoot() = binding

    override fun initData() {

    }

    override fun initView() {
        binding.vpMain.apply {
            adapter = FragPagerAdapter(supportFragmentManager, lifecycle) {
                add(HomeFragment())
                add(MovieFragment())
                add(DiscoveryFragment())
                add(MineFragment())
            }
            isUserInputEnabled = false
        }

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> binding.vpMain.setCurrentItem(0, false)
                R.id.navigation_movie -> binding.vpMain.setCurrentItem(1, false)
                R.id.navigation_discovery -> binding.vpMain.setCurrentItem(2, false)
                R.id.navigation_mine -> binding.vpMain.setCurrentItem(3, false)
            }
            true
        }
    }

}
