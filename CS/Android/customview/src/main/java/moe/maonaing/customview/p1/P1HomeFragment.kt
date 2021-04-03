package moe.maonaing.customview.p1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import moe.maonaing.customview.R
import moe.maonaing.customview.p1.fragment.*
import java.lang.RuntimeException

const val TAG = "P1HomeFragment"
const val ITEM_SIZE = 5

class P1HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private fun initView(view: View) {
        view.apply {
            viewPager = findViewById(R.id.viewPager)
            tabLayout = findViewById(R.id.tabLayout)
        }
    }

    private fun initViewPager() {
        val adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount() = ITEM_SIZE

            override fun createFragment(position: Int) = when(position) {
                0 -> ClearEditTextFragment()
                1 -> FunctionGraphFragment()
                2 -> MaskedFragment()
                3 -> SurfaceViewFragment()
                4 -> CartesianCardioidFragmentFragment.newInstance()
                else -> throw RuntimeException("position 越界！")
            }
        }

        viewPager.adapter = adapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            when(position) {
                0 -> tab.text = ClearEditTextFragment.TITLE
                1 -> tab.text = FunctionGraphFragment.TITLE
                2 -> tab.text = MaskedFragment.TITLE
                3 -> tab.text = SurfaceViewFragment.TITLE
                4 -> tab.text = CartesianCardioidFragmentFragment.TITLE
            }
        }.attach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_p1_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initViewPager()
        setupTabLayout()
    }

    companion object {
        fun newInstance() = P1HomeFragment()
    }

}