package moe.maonaing.viewbinding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import moe.maonaing.viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 获取 binding 对象
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 设置 View
        setContentView(binding.root)

        // 使用
        binding.changeText.setOnClickListener {
            binding.textView.text = "你好，世界！"
        }

        binding.toJavaMainActivity.setOnClickListener {
            val intent = Intent(this, JavaMainActivity::class.java)
            startActivity(intent)
        }

        binding.toFragment.setOnClickListener {
            val intent = Intent(this, MyFragmentActivity::class.java)
            startActivity(intent)
        }

    }

}