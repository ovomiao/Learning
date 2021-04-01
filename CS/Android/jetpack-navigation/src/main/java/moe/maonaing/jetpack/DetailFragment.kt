package moe.maonaing.jetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation


/**
 * 处理调整逻辑
 */
class DetailFragment : Fragment() {
    private lateinit var name: String // 启动参数

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 这里的 name 就是 NavGraph 中定义的 key
        // 这一点和普通的 Fragment 用法是一样的
        name = arguments?.getString("name").toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, name, Toast.LENGTH_SHORT).show()

        // 处理导航
        view.findViewById<Button>(R.id.button).setOnClickListener {
            // findNavController(it) 当前 button(it) 找出当前 button(it) 所属的
            // NavController
            val navController: NavController = Navigation.findNavController(it)
            // 跳转, 使用 Android Studio 拖拽时，这里的 id 是自动生成的，如果手写
            // 也建议使用 action_{current}_to_{target} 的形式命名 id
            // navController.navigate(R.id.homeFragment)
            // 不添加 action（即 NavGraph 的那条线）也是可以跳转的，但还是建议使用 action 跳转
            navController.navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }

}