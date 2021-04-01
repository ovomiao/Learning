package moe.maonaing.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

/**
 * 添加 ActionBar
 *
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 获取 NavController 这里的 id，就是 activity_main 中 NavHost 的 id
        val navController = Navigation.findNavController(this, R.id.fragment)
        // 显示 ActionBar 的返回箭头 <-
        NavigationUI.setupActionBarWithNavController(this, navController)

        // 要自定义 Toolbar 可以看看这个相关的重载方法
        //NavigationUI.setupWithNavController()
    }

    /**
     * 让 <- 支持返回
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.fragment)
        // 支持返回
        return navController.navigateUp()
    }

}