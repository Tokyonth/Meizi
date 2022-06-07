package com.tokyonth.mz.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.github.panpf.sketch.displayImage

fun ImageView.load(url: String) {
    displayImage(url) {
        placeholder(loadingDrawable()!!)
    }
}

private fun loadingDrawable(): Drawable? {
    return null
}
