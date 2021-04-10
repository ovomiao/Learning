package moe.maonaing.customview.p3.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import moe.maonaing.customview.dp2px

class Bird(context: Context, gameWidth: Int, gameHeight: Int, bitmap: Bitmap)
    : DrawablePart(context, gameWidth, gameHeight, bitmap) {

    companion object {
        private const val BIRD_POS_Y = 1 / 2f
        private const val BIRD_WIDTH = 30f
    }

    private val width = context.dp2px(BIRD_WIDTH)
    private val height = width / bitmap.width * bitmap.height

    private var x: Float = gameWidth / 2f - width / 2

    // 需要控制上升下坠
    var y: Float = gameHeight * BIRD_POS_Y


    private val rect = RectF()

    override fun draw(canvas: Canvas) {
        rect.set(x, y, x + width, y + height)
        canvas.drawBitmap(bitmap, null, rect, null)
    }

    /**
     * 在 SurfaceView 重建时，恢复 Bird 的位置
     */
    fun reset() {
        y = gameHeight * BIRD_POS_Y
    }

}