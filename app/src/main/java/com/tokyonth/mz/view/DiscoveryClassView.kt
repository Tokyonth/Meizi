package com.tokyonth.mz.view

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.github.panpf.sketch.displayImage
import com.github.panpf.sketch.transform.CircleCropTransformation

import com.tokyonth.mz.R
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.utils.ktx.dp2px

class DiscoveryClassView : LinearLayout {

    private var title: String = ""

    private var ivLl: LinearLayout? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initAttr(attributeSet!!)
        initView()
    }

    private fun initAttr(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DiscoveryClassView)
        title = typedArray.getString(R.styleable.DiscoveryClassView_discoveryTitle)!!
        typedArray.recycle()
    }

    private fun initView() {
        orientation = VERTICAL
        setPadding(16.dp2px().toInt())
        val tvTitle = TextView(context).apply {
            setTextColor(Color.BLACK)
            textSize = 16f
            text = title
        }
        addView(tvTitle)
        ivLl = LinearLayout(context).apply {
            orientation = HORIZONTAL
            setPadding(0, 16.dp2px().toInt(), 0, 0)
        }
        addView(ivLl)
    }

    private fun buildView(data: AlbumTagEntity): LinearLayout {
        val size = width / 4 - 24
        val iv = ImageView(context).apply {
            layoutParams = LayoutParams(size, size)
            setPadding(8.dp2px().toInt())
            displayImage(data.pic) {
                transformations(CircleCropTransformation())
            }
        }
        val tv = TextView(context).apply {
            textSize = 14f
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            text = data.name
        }
        return LinearLayout(context).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER
            addView(iv)
            addView(tv)
        }
    }

    fun setData(list: List<AlbumTagEntity>) {
        for (index in 0 until 4) {
            val data = list[index]
            ivLl?.addView(buildView(data))
        }
    }

}
