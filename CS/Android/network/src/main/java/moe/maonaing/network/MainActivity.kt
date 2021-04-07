package moe.maonaing.network

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import moe.maonaing.network.databinding.ActivityMainBinding
import moe.maonaing.network.java.okhttp.OkHttpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        startActivity(Intent(this, OkHttpActivity::class.java))
    }

}