package com.tokyonth.mz.base

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem

import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun setVbRoot(): ViewBinding

    protected abstract fun initData()

    protected abstract fun initView()

    open fun initObserve() {}

    open fun isDarkStatusBars(): Boolean = true

    open fun setToolBar(toolbar: Toolbar, barTitle: String) {
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        title = barTitle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            isAppearanceLightStatusBars = isDarkStatusBars()
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
        }
        setContentView(setVbRoot().root)
        //setToolBar()
        initData()
        initView()
        initObserve()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
