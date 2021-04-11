package moe.maonaing.ui_bottom_navigation_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // 注意：这里的 findNavController() 不需要传递 activity 参数是因为
        //      RTX 中 Activity.kt 的扩展函数在起作用。
        //      对于 java 需要使用：
        //          public static NavController findNavController(@NonNull Activity activity, @IdRes int viewId)
        val navigationController = findNavController(R.id.fragment)
        //val navigationController = findNavController(this, R.id.fragment) // java 的方式

        // 配置 Appbar
        // 切换页面时会显示返回按钮
        //val appBarConfiguration = AppBarConfiguration.Builder(navigationController.graph)
        //        .build()

        // 这里传入 BottomNavigationView 的 menu 就不会显示返回按钮了
        val appBarConfiguration = AppBarConfiguration.Builder(bottomNavigationView.menu)
                .build()

        // 直接使用三个页面的 id
//        val appBarConfiguration = AppBarConfiguration.Builder(
//                R.id.homeFragment,
//                R.id.categoryFragment,
//                R.id.meFragment
//        ).build()
//

        // 关联 AppBar 和导航组件
        NavigationUI.setupActionBarWithNavController(
                this,
                navigationController,
                appBarConfiguration
        )
        // 关联 BottomNavigationView 和 Navigation 组件
        // 如果需要使用 Toolbar 自定义 AppBar 也可以使用这个方法组装
        NavigationUI.setupWithNavController(
                bottomNavigationView,
                navigationController
        )
    }

}