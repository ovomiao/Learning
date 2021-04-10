package moe.maonaing.customview.p2.view

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.withTranslation
import moe.maonaing.customview.R
import moe.maonaing.customview.dp2px
import kotlin.math.min

/**
 *  自定义圆形 ProgressBar
 *
 *  *   自定义 View 的 setter 方法需要注意的事项
 *          * 如果是会影响 View 绘制效果或者需要支持属性动画的属性，需要
 *            在调用 setter 赋值后，将当前帧标记为过期帧。让系统在下一次
 *            刷新 View 时，重绘
 *          * 在 Kotlin 中需要特别注意的
 *              * 因为 Kotlin 定义属性即生成访问器，所以使用 Kotlin 定义的
 *                属性如果需要被外界调用要定义为 public，不要习惯于 Java 的
 *                private 权限
 */

private const val KEY_INSTANCE = "key_instance"
private const val KEY_PROGRESS = "key_progress"

class RoundProgressBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        // 进度的最大值
        private const val MAX = 100
    }

    // 颜色：进度条和文字公用
    var color = Color.RED
        set(value) {
            field = value
            invalidate()
        }
    // 进度文字大小: 默认 16dp
    var textSize = context.dp2px(16f).toFloat()
        set(value) {
            field = value
            invalidate()
        }
    // 圆半径
    var radius = context.dp2px(508f).toFloat()
        set(value) {
            field = value
            invalidate()
        }
    // 线条宽度
    var lineWidth = context.dp2px(10f).toFloat()
        set(value) {
            field = value
            invalidate()
        }
    // 进度
    var progress = 0
        set(value) {
            field = value
            invalidate()
        }

    private lateinit var textPaint: Paint

    private lateinit var circlePaint: Paint

    private val rect = RectF()

    // 文本测量后的结果
    private val textBound = Rect()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar)

        typedArray.apply {
            val count = typedArray.indexCount
            for (index in 0 until count) {
                when(getIndex(index)) {
                    R.styleable.RoundProgressBar_bar_color ->
                        color = getColor(R.styleable.RoundProgressBar_bar_color, 0xFF0000)
                    R.styleable.RoundProgressBar_bar_line_width ->
                        lineWidth = getDimension(R.styleable.RoundProgressBar_bar_line_width, lineWidth)
                    R.styleable.RoundProgressBar_android_textSize ->
                        textSize = getDimension(R.styleable.RoundProgressBar_android_textSize, textSize)
                    R.styleable.RoundProgressBar_bar_radius ->
                        radius = getDimension(R.styleable.RoundProgressBar_bar_radius, radius)
                    // 使用了 Android 系统已有的属性，这里会多一个 android 前缀
                    R.styleable.RoundProgressBar_android_progress -> {
                        var p = getInteger(R.styleable.RoundProgressBar_android_progress, 0)
                        if (p > MAX) {
                             p = MAX
                        }
                        progress = p
                    }
                }
            }
        }
        typedArray.recycle()
        // 初始化画笔
        initPaint()
    }

    private fun initPaint() {
        textPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            textSize = this@RoundProgressBar.textSize
            textAlign = Paint.Align.CENTER
            color = this@RoundProgressBar.color
        }

        circlePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = lineWidth
            color = this@RoundProgressBar.color
            // 设置一些笔帽
            strokeCap = Paint.Cap.ROUND
        }
    }

    /**
     * 默认值 48dp -> wrap_content
     *      * 分析：圆环控件的高宽是一致的，默认值无需处理
     *             如果是用户设定了一个固定值或者使用 match_parent
     *             则取高宽较小的一个。
     *             故只需测量一个数值
     *      * 固定值 -> 就是 MeasureSize 值
     *      * wrap_content ->  在超过父 View 的情况下，也是 MeasureSize
     *      * match_parent
     *          * 总 Size - 线宽 - paddingLeft + paddingRight
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        // 取下较小的那个的测量值和模式
        var size = if (widthSize <= heightSize) {
            measureSize(widthSize, widthMode)
        } else {
            measureSize(heightSize, heightMode)
        }
        // 设置最终的测量尺寸
        setMeasuredDimension(size, size)
    }

    /**
     * 因为只是画一个圆，所以 View 的尺寸就是圆直径
     *  即 View 尺寸由外部传入的 radius 决定
     *      * 最大尺寸是画满高或者宽
     *   * 同时测量 View 时，要考虑 View 的 padding 值，
     *     View 的 padding 扩大，那么 View 的尺寸也要扩大
     *     （效果参考 TextView）
     */
    private fun measureSize(size: Int, mode: Int): Int {
        var realSize = size
        if (mode == MeasureSpec.EXACTLY) {
            // 固定高度时，padding 不影响 View 的大小
            return realSize
        } else {
            // 非固定大小就需要考虑 padding 了, 这里只考虑了 padding 一致的情况
            val needSize = (radius * 2).toInt() + paddingStart
            realSize = if (mode == MeasureSpec.AT_MOST) {
                min(needSize, size)
            } else {
                // 这里最好的还是要分 width 和 height 考虑
                needSize
            }
        }
        return realSize
    }

    /**
     * padding 值要在绘制时，考虑
     */
    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            // 绘制背景圆
            drawBackgroundCircle(canvas)
            // 绘制进度
            drawProgress(canvas)
            // 绘制文本
            drawProgressText(canvas)
        }
    }

    // https://www.jianshu.com/p/1728b725b4a6
    private fun drawProgressText(canvas: Canvas) {
        val text = "$progress %"
        // 测量文本的尺寸
        canvas.withTranslation(width / 2.toFloat(), height / 2.toFloat()) {
            textPaint.getTextBounds(text, 0, text.length, textBound)
            // 由于绘制的 y 坐标是基线，所以需要将文本上移 descent/2
            var textHeight = textBound.height() - textPaint.descent()/2f // 文本的高度
            /**
             * 绘制文本这里的 y 坐标代表文字的基线
             *      * 数字的 descent 区域不存在，所以 textHeight / 2 方式就可以
             *        让 View 居中
             */
            canvas.drawText(text, 0f, textHeight/2f, textPaint)
        }
    }

    private fun drawBackgroundCircle(canvas: Canvas) {
        // 线宽度为进度宽度的 1/4
        circlePaint.strokeWidth = lineWidth * 1.0f / 4
        // 圆的实际半径
        val realRadius = radius - paddingStart - circlePaint.strokeWidth/2
        canvas.withTranslation(width / 2.toFloat(), height / 2.toFloat()) {
            drawCircle(0f, 0f, realRadius, circlePaint)
        }
    }

    private fun drawProgress(canvas: Canvas) {
        circlePaint.strokeWidth = lineWidth
        val realRadius = radius - paddingStart
        canvas.withTranslation(width/2f, height/2f) {
            rect.left = -realRadius
            rect.top = -realRadius
            rect.right = realRadius
            rect.bottom = realRadius
            val angle = computeAngle()
            drawArc(rect, 0f, angle, false, circlePaint)
        }
    }

    private fun computeAngle(): Float {
        val ratio = progress * 1f / MAX
        return ratio * 360
    }

    /**
     * 进度值的保存
     */
    override fun onSaveInstanceState(): Parcelable? {
        val parentState = super.onSaveInstanceState()
        // 保存数据
        return Bundle().apply {
            putParcelable(KEY_INSTANCE, parentState)
            putInt(KEY_PROGRESS, progress)
        }
    }

    /**
     * 进度值的恢复
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            state.apply {
                // 恢复 View 保存的数据
                val parentState: Parcelable? = getParcelable(KEY_INSTANCE)
                super.onRestoreInstanceState(parentState)
                // 恢复进度数据
                progress = getInt(KEY_PROGRESS)
            }
            return
        }
        super.onRestoreInstanceState(state)
    }

}