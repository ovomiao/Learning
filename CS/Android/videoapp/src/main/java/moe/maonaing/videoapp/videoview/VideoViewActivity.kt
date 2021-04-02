package moe.maonaing.videoapp.videoview

import android.media.PlaybackParams
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.RequiresApi
import moe.maonaing.videoapp.R
import moe.maonaing.videoapp.VIDEO_URL

// 禁用 Activity 重建： android:configChanges="orientation|screenLayout|screenSize"
// [VideoView] 当当前 Activity 失去焦点，系统就会回收 MediaPlayer，再次切换时，
// 回重新生成 MediaPlayer；探索一下是否是调用 onPause 后系统就回收 MediaPlayer
// VideoView 比较适合当视频背景
class VideoViewActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)

        videoView = findViewById(R.id.videoView)



        // raw 文件夹对应的字符串 Path
        val localVideoPath = "android.resource://$packageName/${R.raw.vi_woman}"

        videoView.setVideoPath(localVideoPath)
        // 使用 Uri
        //videoView.setVideoURI(Uri.parse(localVideoPath))

        // 播放网络视频
        // val videoUrl = VIDEO_URL[0]
        // videoView.setVideoPath(videoUrl)

        // 使用系统自带的控制条
        videoView.setMediaController(MediaController(this))


        videoView.setOnPreparedListener {

            Log.i("TAG", "MediaPlayer: $it")

            it.isLooping = true // 重复播放
            // 跳转到目标位置播放 it.seekTo(100)
            it.playbackParams = PlaybackParams().apply {
                // speed() 变速播放， 2x、3x
                // pitch() 改变音高（可以实现变声: 有点机器人的感受）
                speed = 2f // 2x 播放
            }
        }

        // 视频播放错误回调
        //videoView.setOnErrorListener()

        // 开始播放
        videoView.start()

    }


}