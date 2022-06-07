package com.tokyonth.mz.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.InputFilter.LengthFilter
import android.graphics.RectF
import android.text.InputFilter
import android.util.AttributeSet
import android.view.KeyEvent

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged

import com.tokyonth.mz.R
import com.tokyonth.mz.utils.ktx.dp2px

class PwdEditText : AppCompatEditText {

    companion object {

        private const val DEFAULT_STYLE = 0
        private const val DEFAULT_PWD_COUNT = 6

        private val DEFAULT_DOT_COLOR = Color.parseColor("#6200EE")
        private val DEFAULT_STROKE_COLOR = Color.parseColor("#CCCCCC")
        private val DEFAULT_STROKE_RADIUS = 6.dp2px()
        private val DEFAULT_STROKE_WIDTH = 1.dp2px()
        private val DEFAULT_DOT_RADIUS = 4.dp2px()

    }

    private var style // 控件的样式，矩形或圆角矩形
            = 0
    private var mStrokeRadius // 边框圆角的半径
            = 0f
    private var mStrokeWidth // 边框宽度
            = 0f
    private var mStrokeColor // 边框颜色
            = 0
    private var pwdDotColor // 密码圆点颜色
            = 0
    private var pwdDotRadius // 密码圆点半径
            = 0f
    private var mWidth // 控件宽度
            = 0
    private var mHeight // 控件高度
            = 0
    private var strokePaint // 绘制边框paint
            : Paint? = null
    private var pwdDotPaint // 绘制密码圆点paint
            : Paint? = null
    private var mCount // 密码框个数
            = 0
    private var cellWidth // 每个密码框的宽度
            = 0f
    private var paddingWidth = 0f
    private var halfStrokeWidth = 0f
    private var mCurInputCount // 当前输入字符个数
            = 0

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
        initView()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PwdEditText)
        style = typedArray.getInt(R.styleable.PwdEditText_style, DEFAULT_STYLE)
        mCount = typedArray.getInt(R.styleable.PwdEditText_pwdCount, DEFAULT_PWD_COUNT)
        mStrokeColor =
            typedArray.getColor(R.styleable.PwdEditText_strokeColor, DEFAULT_STROKE_COLOR)
        mStrokeWidth =
            typedArray.getDimension(R.styleable.PwdEditText_strokeWidth, DEFAULT_STROKE_WIDTH)
        mStrokeRadius =
            typedArray.getDimension(R.styleable.PwdEditText_strokeRadius, DEFAULT_STROKE_RADIUS)
        pwdDotColor = typedArray.getColor(R.styleable.PwdEditText_dotColor, DEFAULT_DOT_COLOR)
        pwdDotRadius =
            typedArray.getDimension(R.styleable.PwdEditText_dotRadius, DEFAULT_DOT_RADIUS)
        typedArray.recycle()
    }

    private fun initView() {
        // 初始化边框画笔
        strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = mStrokeColor
            strokeWidth = mStrokeWidth
            style = Paint.Style.STROKE
        }

        // 初始化圆点画笔
        pwdDotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = pwdDotColor
            style = Paint.Style.FILL
        }

        halfStrokeWidth = mStrokeWidth / 2
        // 设置光标不可见
        isCursorVisible = false
        // 设置限定最大长度
        filters = arrayOf<InputFilter>(LengthFilter(mCount))
        maxLines = 1
        isFocusable = false
        setBackgroundColor(Color.TRANSPARENT)
        doOnTextChanged { text, _, _, _ ->
            mCurInputCount = text.toString().length
            // 输入完成的回调
            if (mCurInputCount == mCount) {
                if (onTextInputListener != null) {
                    onTextInputListener!!.onComplete(text.toString())
                }
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        paddingWidth = 15.dp2px()
        val allPaddingWidth = paddingWidth * (mCount - 1)
        cellWidth = (mWidth - mStrokeWidth * mCount - allPaddingWidth) / mCount
    }

    override fun onDraw(canvas: Canvas) {
        drawDotRect(canvas)
        drawPwdDot(canvas)
    }

    private fun drawPwdDot(canvas: Canvas) {
        strokePaint?.color = DEFAULT_DOT_COLOR
        for (index in 1..mCurInputCount) {
            canvas.drawCircle(
                halfStrokeWidth + cellWidth / 2 + (cellWidth + paddingWidth + mStrokeWidth) * (index - 1),
                (mHeight / 2).toFloat(),
                pwdDotRadius,
                pwdDotPaint!!
            )

            drawRect(canvas, index - 1)
        }
    }

    // 绘制矩形方块
    private fun drawDotRect(canvas: Canvas) {
        strokePaint?.color = mStrokeColor
        if (mCount == 1) {
            val rectF = RectF(
                halfStrokeWidth, halfStrokeWidth, mWidth - halfStrokeWidth,
                mHeight - halfStrokeWidth
            )
            canvas.drawRoundRect(rectF, mStrokeRadius, mStrokeRadius, strokePaint!!)
        } else {
            for (index in 0 until mCount) {
                drawRect(canvas, index)
            }
        }
    }

    private fun drawRect(canvas: Canvas, index: Int) {
        // 画框框 左上，右下
        val left = if (index == 0) {
            halfStrokeWidth
        } else {
            halfStrokeWidth * (2 * index) + cellWidth * index + paddingWidth * index
        }
        val top = mStrokeWidth / 2 // 就是halfStrokeWidth，语法不允许int x=0;y=x;
        val right =
            halfStrokeWidth * (2 * index + 1) + cellWidth * (index + 1) + paddingWidth * index
        val bottom = mHeight - halfStrokeWidth
        val rectF = RectF(
            left,
            top,
            right,
            bottom
        )
        canvas.drawRoundRect(rectF, mStrokeRadius, mStrokeRadius, strokePaint!!)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_ENTER) {
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    fun interface OnTextInputListener {

        fun onComplete(result: String)

    }

    private var onTextInputListener: OnTextInputListener? = null

    fun setOnTextInputListener(onTextInputListener: OnTextInputListener) {
        this.onTextInputListener = onTextInputListener
    }

}
