package moe.maonaing.animation.activity

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.transition.Explode
import android.transition.Transition
import android.util.Pair
import moe.maonaing.animation.R

/**
 * Activity 直接的跳转
 *      * 启动者 Activity -> 离场动画
 *      * 被启动者 Activity -> 入场动画
 *
 * * Activity 的转场动画是定义在 ActivityOptions 里面的
 *
 * * Android 预定义的 Activity 转场动画
 *          * fade 淡入淡出
 *              * 进入的 Activity 淡入
 *              * 离场的 Activity 淡出
 *          * slide 平移
 *              * 可以定义平移的方向
 *          * explode 类似百叶窗效果
 *
 * * 设置 Activity 的转场效果的两种方式
 *          * 代码中使用 ActivityOptions
 *          * 资源文件定义
 *
 */
class FirstActivity : AppCompatActivity() , View.OnClickListener {

    override fun onClick(v: View?) {
        var redId = -1
        var title = "加载错误！"
        when (v?.id) {
            R.id.iv_1 -> {
                redId = R.drawable.ic_1
                title = "猫羽雫 一"
            }
            R.id.iv_2 -> {
                redId = R.drawable.ic_2
                title = "猫羽雫 二"
            }
            R.id.iv_3 -> {
                redId = R.drawable.ic_3
                title = "猫羽雫 三"
            }
            R.id.iv_4 -> {
                redId = R.drawable.ic_4
                title = "猫羽雫 四"
            }
        }
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra(SecondActivity.KEY_RES_ID, redId)
            putExtra(SecondActivity.KEY_TITLE_ID, title)
        }
        // 默认转场效果是淡入淡出
        //val activityOptions: ActivityOptions = ActivityOptions
        //        .makeSceneTransitionAnimation(this)

        val transition: Transition = Explode()
        // 排除状态栏的过场动画
        transition.excludeTarget(android.R.id.statusBarBackground, true)

        // public void setEnterTransition(Transition transition)
        // 设置进场动画: 设置了这两个和启动时传入 ActivityOptions 就可以
        // 实现转场动画了
        window.enterTransition = transition
        // 设置离场动画
        window.exitTransition = transition

        // 如需自定义共享元素的进场离场效果可以使用如需设置
        // window.sharedElementEnterTransition = transition
        // window.sharedElementExitTransition = transition

        // 创建一个共享元素
        val shareElement: Pair<View, String> = Pair.create(
                v,          //这里两个 Activity 共享的就是这个 ImageView
                "image"   // tag 是 String 类型的可以自定义
                // 但是这个 tagName 需要在目标 View 中，为共享的元素
                // 例如这里的 ImageView 控件设置 transitionName 属性为 image
                // 需要和这里一致；
        )
        // 需要在这里把共享元素传递给 ActivityOptions
        val activityOptions: ActivityOptions = ActivityOptions
                .makeSceneTransitionAnimation(this, shareElement)

        startActivity(intent, activityOptions.toBundle())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        findViewById<ImageView>(R.id.iv_1).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_2).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_3).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_4).setOnClickListener(this)
    }

}