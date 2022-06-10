package com.tokyonth.mz.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView

import com.github.panpf.sketch.displayImage
import com.github.panpf.sketch.transform.BlurTransformation
import com.github.panpf.sketch.transform.CircleCropTransformation

fun ImageView.load(url: String, isCircle: Boolean = false, isBlur: Boolean = false) {
    return
    displayImage(url) {
        //placeholder(loadingDrawable())
        if (isCircle) {
            transformations(CircleCropTransformation())
        }
        if (isBlur) {
            transformations(BlurTransformation(maskColor = Color.BLACK and 0x20FFFFFF))
        }
    }
}

private fun loadingDrawable(): Drawable? {
    return null
}
