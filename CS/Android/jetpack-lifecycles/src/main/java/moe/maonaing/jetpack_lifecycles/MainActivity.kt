package moe.maonaing.jetpack_lifecycles

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * * Android 系统的中的时间问题：
 *      * SystemClock.elapsedRealtime() -> 返回系统从开机到现在所经过的毫秒数，不受时区影响
 *                          * 此 API 在统计时间段是绝对可靠的
 *      * System.currentTimeMillis() 返回 Unix 时间戳，会随着时区变化而变化，在统计时间段将变得
 *                      不可靠。如果改变系统时间，会影响此 API 的返回值
 *
 * Chronometer#start() 开始计时,并刷新 View
 * Chronometer#stop() 中止 View 的刷新，但并不会结束内部的计时器
 * * 参考： https://www.jianshu.com/p/9630285e7cc7
 */
class MainActivity : AppCompatActivity() {
    private lateinit var chronometer: Chronometer
    private var elapsedRealtime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chronometer = findViewById(R.id.chronometer)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, AppChronometerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        chronometer.start()
        chronometer.base = SystemClock.elapsedRealtime() - elapsedRealtime
    }

    override fun onPause() {
        super.onPause()
        elapsedRealtime = SystemClock.elapsedRealtime() - chronometer.base
        chronometer.stop()
    }

}