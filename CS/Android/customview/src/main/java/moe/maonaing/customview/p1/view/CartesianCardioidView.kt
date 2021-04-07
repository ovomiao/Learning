package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withRotation
import androidx.core.graphics.withScale
import androidx.core.graphics.withTranslation
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import moe.maonaing.customview.R
import kotlin.math.*

private fun Float.toRadians() = this / 180 * PI.toFloat()

// x = 16 * (sin x)^3
// y = 13 cos x - 5 cos 2x - 2 cos 3x - cos 4x

fun Float.heartX() = - 16 * sin(this).pow(3)

fun Float.heartY() = -(13 * cos(this) - 5 * cos(2 * this) - 2 * cos(3 * this) - cos(4 * this))


class CartesianCardioidView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) , LifecycleObserver {

    companion object {
        const val MAX_SCALE = 40f
        const val MIN_SCALE = 5f
    }

    // 绘制的正方形区域的边长
    private var mSize = 0

    private var mAngle = 0f

    private var mCenterX = 0f
    private var mCenterY = 0f

    // 桃心的缩放倍数
    private var mScale = 5f

    private var isZoomIn = true

    private val path = Path()

    private lateinit var rotatingJob: Job

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mSize = min(w, h)
        mCenterX = w/2.toFloat()
        mCenterY = h/2.toFloat()
    }

    private val circlePaint = Paint().apply {
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        color = ContextCompat.getColor(context, R.color.purple_200)
        strokeWidth = 10f
    }

    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = ContextCompat.getColor(context, R.color.purple_700)
        strokeWidth = 5f
    }

    private val heartPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.purple_500)
        strokeWidth = 5f
    }

    private val pointPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, R.color.purple_500)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawAxises(it)
            drawCircle(it)
            drawHart(it)
        }
    }

    private fun drawAxises(canvas: Canvas) {
        val x = mCenterX
        val y = mCenterY
        canvas.withTranslation(x, y) {
            drawLine(-x, 0f, x, 0f, linePaint)
            drawLine(0f, -y, 0f, y, linePaint)
        }
    }

    private fun drawCircle(canvas: Canvas) {
        // 会坐标中心的圆
        // 一共需要容纳 3 个圆
        val radius = mSize / 3 / 2.toFloat()
        canvas.withTranslation(mCenterX, mCenterY) {
            drawCircle(0f, 0f, radius, circlePaint)
        }
        // 画动圆
        canvas.withTranslation(mCenterX, mCenterY) {
            canvas.withRotation(-mAngle) {
                val x = 2 * radius * cos(-mAngle.toRadians())
                val y = 2 * radius * sin(-mAngle.toRadians())

                // 画外部的圆
                drawCircle(x, y, radius, circlePaint)
                // 圆心
                drawCircle(x, y, 20f, pointPaint)
                // 原点到外切圆的连线
                drawLine(0f, 0f, x, y, linePaint)
            }
        }
    }

    private fun drawHart(canvas: Canvas) {
        canvas.withTranslation(mCenterX, mCenterY) {
            var radians = 0f.toRadians()
            val scale = mScale
            path.reset()
            path.moveTo(radians.heartX() * scale, radians.heartY() * scale)
            val offset = 5f.toRadians() // 每次增加 5 弧度
            while (radians < 360f.toRadians()) {
                radians += offset
                path.quadTo(radians.heartX() * scale, radians.heartY() * scale, radians.heartX() * scale, radians.heartY() * scale)
            }
            // 绘制了一帧
            drawPath(path, heartPaint)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun rotating() {
        rotatingJob = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(60)
                mAngle += 5f
                if (mScale <= MAX_SCALE) {
                    // 正常放大
                    if (isZoomIn) {
                        mScale += 1f
                    } else {
                        if (mScale > MIN_SCALE) {
                            mScale -= 1f
                        } else {
                            mScale += 1f
                            isZoomIn = !isZoomIn
                        }
                    }
                } else {
                    // 超过最大缩放倍数，缩小
                    mScale -= 1f
                    isZoomIn = !isZoomIn
                }
                postInvalidate() // 通知重绘
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseRotating() {
        // 停止动画
        rotatingJob?.cancel()
    }

}