package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DiscretePathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.maonaing.customview.R

class BubbleSurfaceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr) {

    private val colors = arrayOf(
            R.color.design_default_color_primary,
            R.color.design_default_color_secondary,
            R.color.design_default_color_primary_variant
    )

    private val circlePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        // 设置路径效果, segmentLength 长度，deviation 程度
        pathEffect = DiscretePathEffect(30f, 20f)
    }

    private data class Bubble(val x: Float, val y: Float, val color: Int, var radius: Float)

    // 记录每次点击的信息
    private val bubbles = mutableListOf<Bubble>()

    init {
        // 运行在 UI 线程之外
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                if (!holder.surface.isValid) {
                    // SurfaceView 还没准备好，这和 onTouchEvent 中不同
                    continue
                }
                val canvas: Canvas? = holder.lockCanvas()
                /**
                 * 【注意】在这里迭代 List 时，onTouch 事件也可能在产生，即 onTouch 中在向 List 中
                 *         添加元素，而这里在迭代 List，最终会产生并发异常（List 的迭代中不允许 List 的
                 *         元素改变）。
                 * 【这里可以生成一个 List 副本，onTouch 的原始 List 负责添加，当系统在刷新 View 时，就会
                 * 在这里调用 List 的 toList() 生成一个副本。这样添加和迭代可以同时进行】
                 *
                 */
                canvas?.drawColor(Color.WHITE)
                bubbles.toList().filter {
                    // 过滤掉无法显示的圆
                    it.radius < 1000
                }.forEach {
                    circlePaint.color = it.color
                    canvas?.drawCircle(it.x, it.y, it.radius, circlePaint)
                    // 半径递增
                    it.radius += 20f
                }
                // 注意这里
                if (holder.surface.isValid) {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val color = ContextCompat.getColor(context, colors.random())
            val bubble = Bubble(it.x, it.y, color, 1f)
            if (bubbles.size > 30) {
                bubbles.removeAt(0) // 移除最早的元素
            }
            bubbles += bubble
        }
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(event)
    }

}