package moe.maonaing.ui_bottom_navigation_view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import moe.maonaing.ui_bottom_navigation_view.R
import moe.maonaing.ui_bottom_navigation_view.toolbar.ToolbarActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
        ).get(HomeViewModel::class.java)

        view.findViewById<Button>(R.id.toolbarActivity).setOnClickListener {
            startActivity(Intent(context, ToolbarActivity::class.java))
        }
    }

}