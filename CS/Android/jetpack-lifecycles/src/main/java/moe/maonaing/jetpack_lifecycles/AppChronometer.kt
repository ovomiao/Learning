package moe.maonaing.jetpack_lifecycles

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.widget.Chronometer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * * 要让自己的类去感知 Activity/Fragment 的生命周期，就需要实现
 *   LifecycleObserver，让自己成为一个生命周期观察者。
 */
class AppChronometer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Chronometer(context, attrs, defStyleAttr), LifecycleObserver {
    private var elapsedRealtime = 0L

    /**
     * 在 Activity 生命周期为 onPause 时，自动回调此方法
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        elapsedRealtime = SystemClock.elapsedRealtime() - base
        stop() // 停止 View 的刷新
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        base = SystemClock.elapsedRealtime() - elapsedRealtime
        start() // 开始刷新 View
    }

}