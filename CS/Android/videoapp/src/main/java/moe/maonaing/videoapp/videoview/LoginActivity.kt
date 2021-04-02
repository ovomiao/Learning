package moe.maonaing.videoapp.videoview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.VideoView
import moe.maonaing.videoapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        actionBar?.hide()

        val videoView = findViewById<VideoView>(R.id.videoView)
        val path = "android.resource://$packageName/${R.raw.vi_woman}"

        videoView.setVideoPath(path)
        videoView.start()
        videoView.setOnPreparedListener {
            it.isLooping = true
        }

    }

}