package moe.maonaing.jetpackviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel 将 Activity/Fragment 独立出来
 * 降低控制器的复杂度
 *
 * * 使用 ViewModel 后，在 Activity 重建时我们不需要手动保存 Activity/Fragment
 *   的数据，但是需要手动恢复数据到 UI 界面上。ViewModel 比 Activity 的生命周期更长
 *   所以 ViewModel 中的数据不会因为 Activity 的重建而丢失。
 *
 */
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var textView: TextView

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textView.text = viewModel.number.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)


        textView = findViewById(R.id.textView)

        findViewById<Button>(R.id.button1).setOnClickListener {
            viewModel.number ++
            textView.text = viewModel.number.toString()
        }

        findViewById<Button>(R.id.button2).setOnClickListener {
            viewModel.number += 2
            textView.text = viewModel.number.toString()
        }
    }

}