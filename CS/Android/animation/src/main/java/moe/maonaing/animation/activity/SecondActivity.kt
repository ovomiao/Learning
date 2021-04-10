package moe.maonaing.animation.activity

import android.os.Bundle
import android.transition.Explode
import android.transition.Transition
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import moe.maonaing.animation.R


class SecondActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SecondActivity"

        const val KEY_RES_ID = "key_res_id"
        const val KEY_TITLE_ID = "key_title_id"
    }

    private lateinit var tv: TextView
    private lateinit var iv: ImageView

    private var redId: Int = -1
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tv = findViewById(R.id.tv)
        iv = findViewById(R.id.iv)

        redId = intent.getIntExtra(KEY_RES_ID, -1)
        title = intent.getStringExtra(KEY_TITLE_ID)

        if (redId < 0) {
            Log.i(TAG, "onCreate: >>> $redId")
            finish()
            return
        }

        // 也可以使用代码设置 iv.transitionName = "image"
        iv.setImageResource(redId)
        tv.text = title


        val transition: Transition = Explode()
        transition.excludeTarget(android.R.id.statusBarBackground, true)

        // public void setEnterTransition(Transition transition)
        // 设置进场动画
        window.enterTransition = transition
        // 设置离场动画
        window.exitTransition = transition
    }

}