package moe.maonaing.animation.reveal

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import moe.maonaing.animation.R
import kotlin.math.max
import kotlin.math.min

class RevealActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var btnReveal: Button

    //////////////////////////////////////////////////////
    // 揭露动画的参数
    // 圆心
    private var cx = 0
    private var cy = 0
    // 半径
    private var startRadius = 0f
    private var endRadius = 0f
    /////////////////////////////////////////////////////


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal)

        initView()

    }

    private fun initView() {
        textView = findViewById(R.id.textView)

        btnReveal = findViewById(R.id.reveal)
        btnReveal.setOnClickListener {
            playUnmaskAnimation()
        }
    }

    private fun playUnmaskAnimation() {
        if (textView.isVisible) {
            revealExit()
            return
        }
        revealEnter()
    }

    /**
     * 以揭露的效果进入
     *      * 具体效果：
     *          * 在 View 的上层一直绘制圆来显示 View，直到达到 endRadius 的值
     *            如果这时 View 还有没被显示出来的，那么这一部分 View 将会被生硬的
     *            显示出来，所以当 View 高宽不一致，取高宽的最大值作为半径的参考值
     *            可能会好点
     *
     *      * 注意事项：
     *          * 在执行显示的揭露动画时，要先将 View 显示出来，然后在开启揭露动画
     */
    private fun revealEnter() {
        // 这里设置揭露圆圆心为 View 的中心，即从中心开始揭露
        cx = textView.width / 2
        cy = textView.height / 2

        startRadius = 0f // 因为 View 是隐藏的，所以起始半径可以为 0
        endRadius = min(textView.width, textView.height) / 2f // 最终的半径为 View 宽高最小值的一半

        val animator: Animator = ViewAnimationUtils.createCircularReveal(
                textView,   //目标 View
                cx,         // 揭露圆的圆心
                cy,
                startRadius,   // 揭露圆的起始半径，因为这里 View 是隐藏的所以可以设为 0
                endRadius      // 揭露圆的结束半径，当揭露圆最终执行到这里时，动画会结束，View
                               // 所以内容会显示出来
        )
        animator.duration = 5000
        // 先将 View 显示出来，然后在执行揭露动画显示
        textView.visibility = View.VISIBLE
        animator.start()
    }

    /**
     * 以揭露的效果退出
     *      * 注意点：
     *          * 揭露动画在隐藏时，要先执行隐藏的揭露动画
     *            然后在揭露动画执行结束后，将 View 隐藏起来
     *          * 所以揭露动画需要监听动画的结束
     *          * 如果先隐藏那么揭露动画将会失效
     */
    private fun revealExit() {
        // 揭露圆的圆心
        cx = textView.width
        cy = textView.height

        startRadius = max(textView.width, textView.height).toFloat()
        endRadius = 0f // 因为是隐藏 View ，所以结束半径是 0

        val animator = ViewAnimationUtils.createCircularReveal(
                textView,
                cx,
                cy,
                startRadius,
                endRadius
        )
        animator.duration = 5000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                // 动画执行完成之后，隐藏 View
                textView.visibility = View.INVISIBLE
            }
        })
        animator.start()
    }
}
