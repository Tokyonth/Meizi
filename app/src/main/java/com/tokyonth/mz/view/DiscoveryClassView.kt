package com.tokyonth.mz.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.setPadding

import com.tokyonth.mz.Constants
import com.tokyonth.mz.R
import com.tokyonth.mz.data.AccurateEntity
import com.tokyonth.mz.data.AlbumTagEntity
import com.tokyonth.mz.ui.activity.AccurateSearchActivity
import com.tokyonth.mz.ui.fragment.search.SearchType
import com.tokyonth.mz.utils.RandomUtils
import com.tokyonth.mz.utils.ktx.dp2px
import com.tokyonth.mz.utils.load

class DiscoveryClassView : LinearLayout {

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

    private var title: String = ""

    private var ivLl: LinearLayout? = null

    private fun initAttr(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DiscoveryClassView)
        title = typedArray.getString(R.styleable.DiscoveryClassView_discoveryTitle)!!
        typedArray.recycle()
    }

    private fun initView() {
        orientation = VERTICAL
        setPadding(16.dp2px().toInt())
        val tvTitle = TextView(context).apply {
            layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setTextColor(Color.BLACK)
            textSize = 16f
            text = title
        }
        val drawable = AppCompatResources.getDrawable(context, R.drawable.ic_round_chevron_right)
        tvTitle.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
        tvTitle.setOnClickListener {
            moreClick?.invoke()
        }
        addView(tvTitle)
        ivLl = LinearLayout(context).apply {
            orientation = HORIZONTAL
            setPadding(0, 16.dp2px().toInt(), 0, 0)
        }
        addView(ivLl)
    }

    private fun buildView(searchType: SearchType, data: AlbumTagEntity): LinearLayout {
        val size = width / 4 - 24
        val iv = ImageView(context).apply {
            layoutParams = LayoutParams(size, size)
            setPadding(8.dp2px().toInt())
            load(data.pic, isCircle = true)
            setOnClickListener {
                Intent(context, AccurateSearchActivity::class.java).apply {
                    putExtra(Constants.INTENT_KEY_SEARCH_TYPE, searchType.name)
                    putExtra(Constants.INTENT_KEY_ALBUM_ID, data.id)
                    putExtra(
                        Constants.INTENT_KEY_ACCURATE,
                        AccurateEntity(data.name, data.text, data.pic)
                    )
                }.let {
                    context.startActivity(it)
                }
            }
        }
        val tv = TextView(context).apply {
            textSize = 14f
            maxLines = 1
            gravity = Gravity.CENTER
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

    fun setData(searchType: SearchType, list: List<AlbumTagEntity>) {
        val indexGroup = RandomUtils.start(4, list.size - 1)
        indexGroup.forEach {
            ivLl?.addView(buildView(searchType, list[it]))
        }
    }

    private var moreClick: (() -> Unit)? = null

    fun setOnMoreClick(moreClick: () -> Unit) {
        this.moreClick = moreClick
    }

}
