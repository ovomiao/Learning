package moe.maonaing.jetpack.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import moe.maonaing.jetpack.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 也可以使用 createNavigateOnClickListener 直接创建一个 View.OnClickListener 对象
        // 这种写法是把 DetailFragment 那种写法自己在内部实现了。（可以看源码）
        view.findViewById<Button>(R.id.button).setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_detailFragment2)
        )
    }

}