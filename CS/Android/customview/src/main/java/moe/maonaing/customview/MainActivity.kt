package moe.maonaing.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import moe.maonaing.customview.p1.P1ViewActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.p1 -> {
                launchActivity(P1ViewActivity::class.java)
            }
        }
    }

    private lateinit var btnToP1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btnToP1 = findViewById(R.id.p1)
        btnToP1.setOnClickListener(this)


    }

    private fun launchActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

}