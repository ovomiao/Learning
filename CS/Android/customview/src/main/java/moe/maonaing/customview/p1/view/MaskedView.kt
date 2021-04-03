package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import moe.maonaing.customview.R
import kotlin.random.Random

class MaskedView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val DRAWABLE_SIZE = 300f
    }

    private val faceBitmap = ContextCompat.getDrawable(context, R.drawable.ic_baseline_face_24)
            ?.toBitmap(DRAWABLE_SIZE.toInt(), DRAWABLE_SIZE.toInt()) // toBitmap() 是 Kotlin 的扩展函数

    private val bitmapPoint = PointF()

    private val path = Path()

    private val paint = Paint().apply {
        // style = Paint.Style.FILL 这是默认值
        color = Color.BLACK
    }

    // 产生一共随机坐标，让 Bitmap 在屏幕内随机出现
    private fun randomPosition() {
        bitmapPoint.x = Random.nextInt(width - DRAWABLE_SIZE.toInt()).toFloat()
        bitmapPoint.y = Random.nextInt(height - DRAWABLE_SIZE.toInt()).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            faceBitmap?.let {
                // 绘制 Bitmap 不需要画笔
                drawBitmap(it, bitmapPoint.x, bitmapPoint.y, null)
                // 绘制路径
                drawPath(path, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when(action) {
                MotionEvent.ACTION_DOWN -> {
                    randomPosition()
                    addPath(this)
                }
                MotionEvent.ACTION_MOVE ->
                    addPath(this)
                MotionEvent.ACTION_UP ->
                    path.reset()
            }
            invalidate() // 重绘
        }
        performClick()
        return true
    }

    private fun addPath(event: MotionEvent) {
        path.reset()
        // CCW 顺时针，CW 逆时针
        path.addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CCW)

        val radius = DRAWABLE_SIZE / 2

        var x = event.x
        x = if (x < radius) radius else x
        x = if (x > width - radius) width - radius else x

        var y = event.y
        y = if (y < radius) radius else y
        y = if (y > height - radius) height - radius else y

        // 这里选择一个相反路径方向，可从上面矩形 Path 中直接抠出一个圆形
        path.addCircle(x, y, 150f, Path.Direction.CW)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            when(action) {
                MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

}