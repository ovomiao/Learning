package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.*
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import moe.maonaing.customview.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * 【Canvas 绘图的两种思维方式】
 *      * 先将画笔移动到目标位置，然会开始绘制
 *          * 例如：在屏幕中心绘制一个圆，则先计算圆心（即屏幕中心），然后绘制
 *      * 先将画布移动到目标位置，在直接在 Canvas 的坐标原点开始绘图
 *          * 例如：在屏幕中心绘制一个圆，可先将 Canvas 从屏幕左上角移动到
 *                  屏幕中心，然后直接在 Canvas 的坐标原点绘制；此时不用考虑
 *                  圆心的位置，只需要考虑半径了
 *          * 实现流程：
 *              * 1. 先将画布移动到目标点
 *              * 2. 在 Canvas 原点开始绘制
 *              * 3. 绘制完成，将 Canvas 移回屏幕左上角
 *          * 具体实现
 *              * Canvas#save() 先将这个画布保存
 *              * Canvas#translate() 或者 Canvas#rotate() 变换画布到目标点
 *              * Canvas#restore() 将 Canvas 恢复到屏幕左上角，避免对后续绘制产生影响
 *                  * 这一步可以理解为在屏幕左上角新建了一个新的画布
 *
 *
 */

private fun Float.toRadians() = this / 180 * PI.toFloat()

class SineFunctionView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), LifecycleObserver {

    private var rotatingJob: Job? = null

    private val sineSamplesPath = Path()

    // 实线画笔
    private val solidLinePaint: Paint = Paint().apply {
        style = Paint.Style.STROKE  // 描边
        strokeWidth = 5f
        flags = Paint.ANTI_ALIAS_FLAG // 开启抗锯齿
        color = ContextCompat.getColor(context, android.R.color.white)
    }

    // 矢量 OC 的绘制画笔
    private val solidVectorPaint: Paint = Paint().apply {
        style = Paint.Style.STROKE  // 描边
        strokeWidth = 5f
        flags = Paint.ANTI_ALIAS_FLAG // 开启抗锯齿
        color = ContextCompat.getColor(context, R.color.purple_200)
    }

    // 单位圆的绘制画笔
    private val dashedLinePaint = Paint().apply { 
        style = Paint.Style.STROKE
        //TODO： ？【查一下具体解释】 线长 10px， 间接 10px
        pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        color = ContextCompat.getColor(context, R.color.purple_700)
        strokeWidth = 10f
    }

    // 文字画笔
    private val textPaint: Paint = Paint().apply {
        strokeWidth = 50f
        flags = Paint.ANTI_ALIAS_FLAG
        textSize = 50f
        color = ContextCompat.getColor(context, android.R.color.white)
    }

    // 绘制 X-轴、单位圆半径上的投影点
    private val filledCirclePaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, android.R.color.white)
    }

    // 用于记录 View 测量后的高宽
    private data class ViewSize(var width: Float = 0f, var height: Float = 0f)
    private val mViewSize = ViewSize()

    // 单位圆的半径
    private var mRadius = 0f

    // 画布的角度，用于绘制矢量
    private var mAngle = 10f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 获取 View 最终的尺寸
        mViewSize.width = w.toFloat()
        mViewSize.height = h.toFloat()
        mRadius = if (w < h / 2) w / 2.toFloat() else h / 4.toFloat()
        mRadius -= 20f
    }

    /**
     * 技巧：借助 Photoshop 的制图技巧，没一个操作放在一个新的图层，每一个
     *      图层可以封装为一个函数
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            // 给个背景
            it.drawColor(Color.BLACK)
            // 画坐标系
            drawAxises(canvas)
            // 画文字
            drawLabel(canvas)
            // 画单位圆
            drawDashedCircle(canvas)

            // 画矢量（即圆周到圆心的连线）
            drawVector(canvas)

            // 画动点
            drawProjections(canvas)

            // 绘制正弦函数
            drawSineFunction(canvas)
        }
    }

    /**
     * 此扩展函数定义在 androidx.core.graphics.Canvas.kt 文件中
     * Kotlin 提供的扩展函数
     *      * 本质就是对 Java 中的那三种方式，使用 扩展函数做了一个封装
     *      * CanvasKt#withTranslation(x, y) {
     *              // 绘制逻辑
     *       }
     *      * 变换恢复，在扩展函数内部以及完成了，这类只管给定一个目标位置
     *        和绘制逻辑即可。
     */
    private fun drawAxises(canvas: Canvas) {
        val x = mViewSize.width / 2
        val y = mViewSize.height / 2

        // 将 Canvas 原点移动到屏幕中心
        canvas.withTranslation(x, y) {
            // 画中心 x-轴
            drawLine(-x, 0f, x, 0f, solidLinePaint)
            // 画 y-轴
            drawLine(0f, -y, 0f, y, solidLinePaint)
        }

        // 在屏幕 3/4 高度处绘制一条横线
        canvas.withTranslation(x, 3 * mViewSize.height / 4) {
            drawLine(-x, 0f, x, 0f, solidLinePaint)
        }
    }

    // 绘制文字
    private fun drawLabel(canvas: Canvas) {
        solidLinePaint.color = Color.WHITE
        canvas.drawRect(100f, 100f, 600f, 250f, solidLinePaint)
        canvas.drawText("指数函数与旋转矢量", 120f, 195f, textPaint)
    }

    private fun drawDashedCircle(canvas: Canvas) {
        canvas.withTranslation(mViewSize.width/2, 3 * mViewSize.height / 4) {
            drawCircle(0f, 0f, mRadius, dashedLinePaint)
        }
    }

    private fun drawVector(canvas: Canvas) {
        // 先把 Canvas 移动到圆心
        canvas.withTranslation(mViewSize.width/2, 3 * mViewSize.height / 4) {
            /**
             * 连接上圆心和圆周上一点后，旋转 Canvas 就是可以达到半径绕圆周运动的效果。
             *      * 举例理解：假设需要画一条半径与 X-轴为 45 度。如果不使用原始旋转的
             *      方式则需要计算：终点的坐标
             *          * x = mRadius * cos 45
             *          * y mRadius * sin 45
             *          * 动态坐标 (mRadius * cos mAngle, mRadius * sin mAngle)
             *      * 使用旋转画布方式：只需要直接连接圆心和圆周与 X-轴的焦点即可
             *          即坐标为 (0, 0) -> (radius, 0)
             *          * 然后让 Canvas 旋转 45 度即可。如果需要这条线一直动，只需要让 Canvas 旋转
             *           的角度改变即可。
             *              * 不理解可是试试：在一张纸上画一个圆和一条半径，然后旋转纸张，观察结果
             *                  * 这里的纸张就是 Canvas
             */
            canvas.withRotation(-mAngle) {
                // 连接圆心和圆周与 X 轴的交点
                drawLine(0f, 0f, mRadius, 0f, solidVectorPaint)
            }
        }
    }

    /**
     * 这是 Kotlin 的协程，Java 试试属性动画实现
     *      * 执行动画需要注意的是，在 Activity/Fragment
     *          onPause 停止动画，在 onResume 恢复动画
     *      * 可以让本 View 实现 LifecycleObserver 来感知 Activity/Fragment 的
     *        生命周期
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRotating() {
        rotatingJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(60)
                mAngle += 5f
                postInvalidate() // 通知重绘
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseRotating() {
        // 停止动画
        rotatingJob?.cancel()
    }

    private fun drawProjections(canvas: Canvas) {
        // 即屏幕中心坐标系为 o1，单位圆圆心所对应的坐标系为 c1
        //      -> 无论是什么情况，都有 x(o1) = x(c1)
        //      -> x(c1) = mRadius * cos mAngle
        //    由于每次都是先将坐标系移过去，而两个点在 y 方向一直不变
        //     所以 y === 0
        // [注意] Math.cos(a) -> a 是指弧度制

        // 绘制 o1 坐标系上动点
        canvas.withTranslation(mViewSize.width/2, mViewSize.height/2) {
            drawCircle(mRadius * cos(mAngle.toRadians()), 0f,
                    10f, filledCirclePaint)
        }

        // 绘制 c1 坐标系上的动点
        canvas.withTranslation(mViewSize.width / 2, 3 * mViewSize.height / 4) {
            drawCircle(mRadius * cos(mAngle.toRadians()), 10f,
                    10f, filledCirclePaint)
        }

        // 将坐标原点移动到圆周上的一点
        // 1. 先移动到圆心
        canvas.withTranslation(mViewSize.width / 2, 3 * mViewSize.height / 4) {
            // 2.  移动到圆周上
            val x = mRadius * cos(mAngle.toRadians())
            val y = mRadius * sin(mAngle.toRadians())
            canvas.withTranslation(x, -y) {
                drawLine(0f, 0f, 0f, y, filledCirclePaint)
                drawLine(0f, 0f, 0f, -mViewSize.height / 4 + y, solidLinePaint)
            }
        }
    }

    private fun drawSineFunction(canvas: Canvas) {
        canvas.withTranslation(mViewSize.width / 2, mViewSize.height / 2) {
            // 样本数量
            val samplesCount = 80
            // 将 -y 半轴微分为 80 份，即在 -y 轴一共产生 80 个点
            val dy = mViewSize.height / 2 / samplesCount
            sineSamplesPath.reset()
            // 起始点是 x 轴上的一点，故坐标为 (mRadius * cos a, 0)
            sineSamplesPath.moveTo(mRadius * cos(mAngle.toRadians()), 0f)
            // 将 80 个点添加到 Path
            repeat(samplesCount) {
                val x = mRadius * cos(it * -0.15f + mAngle.toRadians())
                val y = -dy * it
                sineSamplesPath.quadTo(x, y, x, y)
            }
            // 绘制一帧
            drawPath(sineSamplesPath, solidVectorPaint)
            drawTextOnPath("f(y) = sin y", sineSamplesPath, 1000f, -50f, textPaint)
        }
    }

    /**
     * Java 的绘制方式
     */
    private fun drawAxisesJava(canvas: Canvas) {
        // 1. 保存画布
        canvas.save()
        // 2. 变换画布到目标点
        canvas.translate(50f, 50f)
        ////////////////////////////////////////////////////////
        // 3. 一系列绘制逻辑
        canvas.drawLine(0f, 0f, 55f, 0f, solidLinePaint)
        ///////////////////////////////////////////////////

        // 4. 恢复画布，即移动回屏幕左上角；目的为了不影响下次绘制
        canvas.restore()
    }


}
