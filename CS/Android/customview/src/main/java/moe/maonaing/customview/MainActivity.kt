package moe.maonaing.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import moe.maonaing.customview.p1.P1ViewActivity
import moe.maonaing.customview.p2.P2Activity
import moe.maonaing.customview.p3.CircleSurfaceViewActivity
import moe.maonaing.customview.p3.game.GameActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.p1 -> {
                launchActivity(P1ViewActivity::class.java)
            }
            R.id.p2 -> {
                launchActivity(P2Activity::class.java)
            }
            R.id.p3CircleSurfaceView ->
                launchActivity(CircleSurfaceViewActivity::class.java)
            R.id.p3Game ->
                launchActivity(GameActivity::class.java)
        }
    }

    private lateinit var btnToP1: Button
    private lateinit var btnToP2: Button
    private lateinit var btnP3CircleSurfaceView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btnToP1 = findViewById(R.id.p1)
        btnToP1.setOnClickListener(this)

        btnToP2 = findViewById(R.id.p2)
        btnToP2.setOnClickListener(this)

        btnP3CircleSurfaceView = findViewById(R.id.p3CircleSurfaceView)
        btnP3CircleSurfaceView.setOnClickListener(this)

        findViewById<Button>(R.id.p3Game).setOnClickListener(this)
    }

    private fun launchActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

}