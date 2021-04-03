package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import moe.maonaing.customview.R
import kotlin.random.Random

class MySurfaceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val colors = intArrayOf(
            R.color.design_default_color_primary,
            R.color.design_default_color_secondary,
            R.color.design_default_color_primary_variant
    )

    private val circlePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    // 记录手指点击的坐标
    private val center = PointF(200f, 200f)

    private var radius = Random.nextFloat() * 200 + 50f

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            repeat(2000) {
                circlePaint.color = ContextCompat.getColor(context,  colors.random())
                drawCircle(center.x, center.y, radius + it, circlePaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            center.x = x
            center.y = y
        }
        invalidate() // 将当前帧标记的为过期，让系统在下次刷新
        // View 时，回调 onDraw()
        performClick()
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(event)
    }

}