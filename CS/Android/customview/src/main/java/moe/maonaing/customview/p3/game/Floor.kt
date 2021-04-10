package moe.maonaing.customview.p3.game

import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.core.graphics.withTranslation

/**
 * Floor 横向重复，纵向拉伸
 */
class Floor(context: Context, gameWidth: Int, gameHeight: Int, bitmap: Bitmap)
    : DrawablePart(context, gameWidth, gameHeight, bitmap) {

    companion object {
        private const val RADIO_POS_Y = 4/5f
    }

    var x: Int = 0
        set(value) {
            field = value
            if (-value > gameWidth) {
                // 让图片始终在屏幕内，超过屏幕外的就没有必要绘制了
                field = value % gameWidth
            }
            Log.i("floor", ">>> $field")
        }

    private var y: Int = (gameHeight * RADIO_POS_Y).toInt()

    private val bitmapShader = BitmapShader(
            bitmap,
            Shader.TileMode.REPEAT,         // x-重复
            Shader.TileMode.CLAMP           // y-拉伸
    )

    private val paint = Paint().apply {
        isAntiAlias = true
        isDither = true
    }

    override fun draw(canvas: Canvas) {
        canvas.withTranslation(x.toFloat(), y.toFloat()) {
            paint.shader = bitmapShader
            drawRect(x.toFloat(), 0f,
                    -x + gameWidth.toFloat(), // -x 为了动态修改 x 值，实现 x 轴运动
                    gameHeight - y.toFloat(),
                    paint
            )
            paint.shader = null
        }
    }

}