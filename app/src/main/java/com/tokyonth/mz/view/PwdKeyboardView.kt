package com.tokyonth.mz.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources

import com.tokyonth.mz.R

class PwdKeyboardView : KeyboardView, OnKeyboardActionListener {

    companion object {

        private const val KEY_EMPTY = -10

    }

    private val delKeyBackground = Color.parseColor("#F5F5F5")

    private var keyIconRect: Rect? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        val keyboard = Keyboard(context, R.xml.keyboard_password) // 初始化 keyboard
        setKeyboard(keyboard)
        isEnabled = true
        isFocusable = true
        isPreviewEnabled = false // 设置点击按键不显示预览气泡
        onKeyboardActionListener = this
    }

    /**
     * 重新绘制删除按键和空白键
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val keys = keyboard.keys
        for (key in keys) {
            if (key.codes[0] == KEY_EMPTY) {
                // 绘制空白键背景
                drawKeyBackground(key, canvas, delKeyBackground)
            }
            val icon =
                AppCompatResources.getDrawable(context, R.drawable.ic_round_keyboard_backspace)
            if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                // 删除删除按键背景
                drawKeyBackground(key, canvas, delKeyBackground)
                // 绘制删除按键图标
                drawKeyIcon(
                    key,
                    canvas,
                    icon
                )
            }
        }
    }

    /**
     * 绘制按键的背景
     */
    private fun drawKeyBackground(key: Keyboard.Key, canvas: Canvas, color: Int) {
        val drawable = ColorDrawable(color)
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
        drawable.draw(canvas)
    }

    /**
     * 绘制按键的 icon
     */
    private fun drawKeyIcon(key: Keyboard.Key, canvas: Canvas, iconDrawable: Drawable?) {
        if (iconDrawable == null) {
            return
        }
        // 计算按键icon 的rect 范围
        if (keyIconRect == null || keyIconRect!!.isEmpty) {
            // 得到 keyIcon 的显示大小，因为图片放在不同的drawable-dpi目录下，显示大小也不一样
            val intrinsicWidth = iconDrawable.intrinsicWidth
            val intrinsicHeight = iconDrawable.intrinsicHeight
            var drawWidth = intrinsicWidth
            var drawHeight = intrinsicHeight
            // 限制图片的大小，防止图片按键范围
            if (drawWidth > key.width) {
                drawWidth = key.width
                // 此时高就按照比例缩放
                drawHeight = (drawWidth * 1.0f / intrinsicWidth * intrinsicHeight).toInt()
            } else if (drawHeight > key.height) {
                drawHeight = key.height
                drawWidth = (drawHeight * 1.0f / intrinsicHeight * intrinsicWidth).toInt()
            }
            // 获取图片的 x,y 坐标,图片在按键的正中间
            val left = key.x + key.width / 2 - drawWidth / 2
            val top = key.y + key.height / 2 - drawHeight / 2
            keyIconRect = Rect(left, top, left + drawWidth, top + drawHeight)
        }
        if (keyIconRect != null && !keyIconRect!!.isEmpty) {
            iconDrawable.bounds = keyIconRect!!
            iconDrawable.draw(canvas)
        }
    }

    override fun onPress(primaryCode: Int) {}

    override fun onRelease(primaryCode: Int) {}

    /**
     * 处理按键的点击事件
     */
    override fun onKey(primaryCode: Int, keyCodes: IntArray) {
        if (primaryCode == KEY_EMPTY) {
            return
        }
        if (listener != null) {
            if (primaryCode == Keyboard.KEYCODE_DELETE) {
                listener!!.onDelete()
            } else {
                listener!!.onInput(primaryCode.toChar().toString())
            }
        }
    }

    override fun onText(charSequence: CharSequence) {}

    override fun swipeLeft() {}

    override fun swipeRight() {}

    override fun swipeDown() {}

    override fun swipeUp() {}

    interface OnKeyListener {
        // 输入回调
        fun onInput(text: String)

        // 删除回调
        fun onDelete()
    }

    private var listener: OnKeyListener? = null

    fun setOnKeyListener(listener: OnKeyListener) {
        this.listener = listener
    }

}
