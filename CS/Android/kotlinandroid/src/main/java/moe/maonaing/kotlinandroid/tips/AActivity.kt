package moe.maonaing.kotlinandroid.tips

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import moe.maonaing.kotlinandroid.R

/**
 * 【小技巧：借助 apply 简化 Intent 添加数据】
 */
private const val KEY_1 = "key_1"
private const val KEY_2 = "key_2"
private const val KEY_3 = "key_3"
private const val KEY_4 = "key_4"

class AActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
    }

    companion object {
        @JvmStatic
        fun launch(
                context: Context,
                data1: Int,
                data2: String,
                data3: Boolean,
                data4: Int
        ) {
            // 使用 apply 简化
            val intent = Intent().apply {
                putExtra(KEY_1, data1)
                putExtra(KEY_2, data2)
                putExtra(KEY_3, data3)
                putExtra(KEY_4, data4)
            }
            context.startActivity(intent)
        }
    }

}