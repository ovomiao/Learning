package moe.maonaing.jetpack_lifecycles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AppChronometerActivity : AppCompatActivity() {

    private lateinit var appChronometer: AppChronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_chronometer)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            finish()
        }

        appChronometer = findViewById(R.id.chronometer)
        appChronometer.start()

        // 将自己的组件注册为生命周期的观察者
        lifecycle.addObserver(appChronometer)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(appChronometer)
    }

}