package moe.maonaing.customview.p1.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import moe.maonaing.customview.R

/**
 * Kotlin 生成的 Style 默认是 0，在继承系统控件时，要修改为 android.R.attr.xxx
 * 否则使用 XML 回崩溃或者丢失原有的样式
 */
class ClearEditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : androidx.appcompat.widget.AppCompatEditText(context, attrs, defStyleAttr) {

    // Drawable 四周增加的响应区域
    private val OFFSET = 20

    // 注意这是可空的
    private var clearDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_baseline_clear_24)

    init {
        context.obtainStyledAttributes(
                attrs,
                R.styleable.ClearEditText,
                defStyleAttr,
                0
        ).apply {
            val clearDrawableId = getResourceId(R.styleable.ClearEditText_clear_drawable, 0)
            if (clearDrawableId != 0) {
                // 外界设置了 clear_drawable 就使用外界的值
                clearDrawable = ContextCompat.getDrawable(context, clearDrawableId)
            }
        }.recycle() // 回收资源
    }

    /**
     * 控制清除 icon 是否显示
     *      如果有文本则显示，没有隐藏；同时只有 EditText 获取焦点时，才去显示，失去焦点就隐藏
     *      icon 显示位置：在文字结束位置
     *          * 中文、英文这种从左到右阅读式文字，icon 应该位于右边，即 right、end（api 17 新增）
     *          * 阿拉伯语这类从右到左的文字，icon 应该位于左边，即 left、end
     *
     *  setCompoundDrawablesRelativeWithIntrinsicBounds()
     *      Intrinsic 系列函数表示使用图片的原始尺寸显示。例如 svg 中定义的 24dp
     *      好处，无需将 dp -> px
     *  setCompoundDrawablesRelative()
     *      * 没有 Intrinsic 的函数，需要手动指定 icon 的像素值
     *  Relative 系列函数和没有 Relative 函数的区别是 ->
     *      Relative 的左右参数是 start、end 考虑到了阿拉伯文字
     *      没有 Relative 的参数是 left、right；尽量使用 Relative 系列函数
     */
    private fun showClearIcon() {
        val icon = if (isFocused && text?.isNotEmpty() == true) clearDrawable else null
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, icon, null)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        // 在 EditText 文字改变时，调用
        showClearIcon()
    }

    /**
     * 触摸到 ClearIcon 清除 EditText 的内容：
     *      * 清除条件： ClearIcon 不为 null 且手指触摸到 ClearIcon.
     *      * 具体实现
     *          * 只有当手指触摸到 ClearIcon 所在的正方形区域才会执行，清除文本操作
     *              * 水平触摸区间：[EditText.Width - ClearDrawable.Width, EditText.Width]
     *              * 垂直触摸区域：[EditText.height/2 - ClearDrawable.height/2， EditText.height/2 + ClearDrawable.height/2]
     *      * 体验优化：
     *          * 不应该手指按下就清除，要考虑用户取消清除，故应该在手指离开屏幕时，清除文本
     *          * 容错优化：应当适当扩大触摸的响应区域，不应该 Drawable 多个就在多大范围内响应
     *              * 例如：24dp 的 drawable，不能只在 24dp 范围内响应，容易让手指按不到
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { e ->
            clearDrawable?.let {
                if (
                    e.action == MotionEvent.ACTION_UP   // 必须时手指抬起才去清除
                    // 手指在 Drawable 的 left 到 right 之前
                    && e.x > width - it.intrinsicWidth - OFFSET // 向左偏移一点，扩大响应区域
                    && e.x < width + OFFSET // 向右偏移
                    // 垂直范围
                    && e.y > height / 2 - it.intrinsicHeight / 2 - OFFSET
                    && e.y < height / 2 + it.intrinsicHeight / 2 + OFFSET
                ) {
                    // 清除文字
                    text?.clear()
                }
            }
        }
        // 注意：有些设备没有触摸，但也能实现 Click 事件，所以在重写了 onTouchEvent 方法后，
        // 需要在重写 performClick(), 并在 onTouchEvent() 中调用 performClick()
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    // 处理焦点问题
    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        // 焦点变化时，改变 ClearIcon 的显示
        showClearIcon()
    }

}