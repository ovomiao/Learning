package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import moe.maonaing.customview.R
import kotlin.random.Random

/**
 * 独立于主线程之外，不和主线程的控件公用绘图表面
 * 一般用于大量的图形绘制
 *
 * SurfaceView 每次重绘并不会清空画布的内容，如果需要每次都清空画布
 * 可以绘制一个背景清空画布
 */
class CircleSurfaceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr) {

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            center.x = x
            center.y = y
        }
        //  public SurfaceHolder getHolder()
        //      Holder -> SurfaceView 的内部成员
        //   Holder#lockCanvas(): Canvas
        val canvas = holder.lockCanvas()
        myDraw(canvas)
        // 释放 Canvas
        holder.unlockCanvasAndPost(canvas)
        return true
    }

    private fun myDraw(canvas: Canvas) {
        repeat(2000) {
            canvas.drawColor(Color.WHITE)
            circlePaint.color = ContextCompat.getColor(context,  colors.random())
            canvas.drawCircle(center.x, center.y, radius + it, circlePaint)
        }
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(event)
    }
}