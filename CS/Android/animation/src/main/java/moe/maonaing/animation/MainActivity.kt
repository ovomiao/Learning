package moe.maonaing.animation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import moe.maonaing.animation.activity.FirstActivity
import moe.maonaing.animation.reveal.RevealActivity
import moe.maonaing.animation.transition.TransitionActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.reveal -> launchActivity(RevealActivity::class.java)
            R.id.viewTransition -> launchActivity(TransitionActivity::class.java)
            R.id.activityTransition -> launchActivity(FirstActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.reveal).setOnClickListener(this)
        findViewById<Button>(R.id.viewTransition).setOnClickListener(this)
        findViewById<Button>(R.id.activityTransition).setOnClickListener(this)
    }

    private fun launchActivity(clz: Class<*>) {
        startActivity(Intent(this, clz))
    }

}