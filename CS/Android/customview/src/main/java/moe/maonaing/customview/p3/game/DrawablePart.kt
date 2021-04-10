package moe.maonaing.customview.p3.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas

abstract class DrawablePart(
        val context: Context,
        val gameWidth: Int,
        val gameHeight: Int,
        val bitmap: Bitmap) {

    abstract fun draw(canvas: Canvas)

}