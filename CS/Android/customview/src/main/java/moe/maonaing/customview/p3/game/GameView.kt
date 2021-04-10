package moe.maonaing.customview.p3.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.DrawableRes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import moe.maonaing.customview.R
import moe.maonaing.customview.dp2px

/**
 * 一旦 SurfaceView 被切换到后台，他就会被回收
 */
class GameView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    companion object {
        private const val TAG = "GameView"

        // 每次触摸小鸟上升的距离
        private const val BIRD_TOUCH_UP_SIZE = -16f
        // 小鸟自由下落的距离
        private const val BIRD_AUTO_DOWN_SIZE = 2f
    }

    private var birdUpSize = context.dp2px(BIRD_TOUCH_UP_SIZE)
    private var autoDownSize = context.dp2px(BIRD_AUTO_DOWN_SIZE)
    private var birdY = 0f

    private lateinit var job: Job

    private lateinit var backgroundBitmap: Bitmap
    private lateinit var birdBitmap: Bitmap
    private lateinit var floorBitmap: Bitmap

    // Bitmap 绘制的矩形区域，应该充满屏幕
    private val bitmapDestRect = RectF()

    private lateinit var floor: Floor
    private lateinit var bird: Bird

    // 地板的运动速度 2dp/次
    private val speed = context.dp2px(2f)


    init {
        holder.addCallback(this)
        initBitmap()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        job = CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                val start = System.currentTimeMillis()
                onRefreshView()
                val end = System.currentTimeMillis()
                val frameTime = end - start
                if (frameTime < 16) {
                    SystemClock.sleep(16 - frameTime)
                }
            }
        }

        // 重建 SurfaceView 的处理
        bird.reset()
        birdY = 0f
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.i(TAG, "surfaceDestroyed: SurfaceView 被销毁了 >>> ")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            birdY = birdUpSize.toFloat()
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapDestRect.set(0f, 0f, w.toFloat(), h.toFloat())

        // 初始化 floor 和 bird
        floor = Floor(context, w, h, floorBitmap)
        bird = Bird(context, w, h, birdBitmap)
    }

    private fun initBitmap() {
        backgroundBitmap = getBitmap(R.drawable.bg1)
        birdBitmap = getBitmap(R.drawable.b1)
        floorBitmap = getBitmap(R.drawable.floor_bg)
    }

    // @DrawableRes 必须传入 Drawable Id
    private fun getBitmap(@DrawableRes resId: Int) = BitmapFactory.decodeResource(resources, resId)

    private fun gameOnDraw(canvas: Canvas) {
        // 绘制背景
        drawBackground(canvas)
        logic()
        // 绘制小鸟
        drawBird(canvas)
        // 绘制地板
        drawFloor(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawBitmap(backgroundBitmap, null, bitmapDestRect, null)
    }

    /**
     * 地板只会左右运动，所以改变其 x-轴坐标即可
     */
    private fun drawFloor(canvas: Canvas) {
        floor.draw(canvas)
    }

    private fun drawBird(canvas: Canvas) {
        bird.draw(canvas)
    }

    private fun logic() {
        floor.x = floor.x - speed

        // 加速下落
        birdY += autoDownSize
        bird.y += birdY
    }

    private fun onRefreshView() {
        var canvas: Canvas? = null
        try {
            canvas = holder.lockCanvas()
            canvas?.let {
                Log.i(TAG, "gameOnDraw: 刷新 View >>> ")
                gameOnDraw(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            canvas?.let {
                holder.unlockCanvasAndPost(it)
            }
        }
    }

}