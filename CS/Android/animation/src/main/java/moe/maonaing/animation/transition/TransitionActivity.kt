package moe.maonaing.animation.transition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import moe.maonaing.animation.R

/**
 * Scene 场景
 *      * View 树已经此 View 树下所有的 View 状态视为一个场景
 * Transition 转换效果（Android 预定义的效果）
 *      * Fade -> 淡入淡出，View 透明度 0 -> 1, 1 -> 0
 *      * ChangeBounds
 *          * 变换边界：可能是 View 的大小变化，位置变化，也可能是二者结合变化
 *      * AutoTransition
 *          * 不指定转场效果时，使用默认的效果
 * * Transition 的定义
 *      * 在代码中定义
 *      * 在资源文件中定义（推荐）
 *          * 可以复用
 *          * 复杂度低
 *          * 新建时，选择 res/transition 目录即可
 * * TransitionManager 控制转场
 *      * TransitionManager.go(targetScene, transition)
 *          * targetScene 需要跳转到的场景
 *          * transition 可选，不设置就是用系统的默认的变化效果
 *              （即 AutoTransition）来播放进场效果和离场效果
 *
 */
class TransitionActivity : AppCompatActivity() {
    private lateinit var overScene: Scene
    private lateinit var infoScene: Scene

    private lateinit var transition: Transition

    fun onClick(v: View?) {
        when (v?.id) {
            R.id.toInfo -> {
                TransitionManager.go(infoScene, transition)
            }
            R.id.fabBack ->
                TransitionManager.go(overScene, transition)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition)

        // 获取 xml 中自定义的 Transition 描述文件，并解析为 Transition 对象
        transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.transition)

        // 场景 View
        val viewRoot = findViewById<FrameLayout>(R.id.root_scene)
        overScene = Scene.getSceneForLayout(
                viewRoot,                   // 场景发生的根 View
                R.layout.scene_overview,    // 预览布局
                this
        )

        infoScene = Scene.getSceneForLayout(
                viewRoot,
                R.layout.scene_info,        // Info 布局
                this                  // Context
        )
        // 默认跳转到 over 场景
        // TransitionManager.go(overScene)  默认变换效果
        TransitionManager.go(overScene, transition)
    }
}