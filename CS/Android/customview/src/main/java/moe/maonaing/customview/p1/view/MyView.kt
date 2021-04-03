package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View

private const val TAG = "MyView"

/**
 *
 * 回调顺序同下：
 *      onFinishInflate() -> onAttachedToWindow() -> onMeasure()
 *          -> onSizeChanged() -> onLayout() -> onDraw() -> onDetachedFromWindow()
 */
class MyView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * View 从 XML 中加载完成后，回调此函数；
     * 使用代码创建 View 不会回调此方法.
     * 即 XML 解析完成
     */
    override fun onFinishInflate() {
        super.onFinishInflate()
        Log.i(TAG, "onFinishInflate: >>> ")
    }

    /**
     * View 被添加到 Window
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(TAG, "onAttachedToWindow: >>> ")
    }

    /**
     * 测量自身尺寸
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(0, 0)
        Log.i(TAG, "onMeasure: >>> ")
    }


    /**
     * 自身尺寸测量完毕
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.i(TAG, "onSizeChanged: ")
    }

    /**
     * 父 View（即 ViewGroup） 回调此方法，摆放这个 View
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.i(TAG, "onLayout: >>> ")
    }

    /**
     * 当下一帧来临时，执行这个方法，开始绘制
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i(TAG, "onDraw: >>> ")
    }

    /**
     * View 从 Window 移除
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(TAG, "onDetachedFromWindow: >>> ")
    }

}