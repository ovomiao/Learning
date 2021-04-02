package moe.maonaing.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

const val TAG = "tag"

/**
 *
 * ViewPager2 + Fragment 的使用
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val MAX_ITEM = 4
    }

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2 = findViewById(R.id.viewPager2)
        tabLayout = findViewById(R.id.tabLayout)

        initViewPager2()
    }

    private fun initViewPager2() {
        // 和一代一样，设置 Fragment 适配器。
        // 需要注意的是这是 androidx.viewpager2.adapter 包下的
        viewPager2.adapter = object : FragmentStateAdapter(this) {

            // ViewPager2 中页面的个数
            override fun getItemCount() = MAX_ITEM

            // 返回当前索引对应的 Fragment，只有在目标示例没有才会创建
            // 不会直接创建的
            override fun createFragment(position: Int) = when(position) {
                1 -> HotFragment()
                2 -> MovieFragment()
                3 -> ChangeFragment.newInstance()
                else -> LiveFragment()
            }
        }

        setupTabLayout()

        // 监听 ViewPager2 的滑动
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.i(TAG, "滑动了 ViewPager2: >>> position = $position, position 偏移：滑动了 " +
                        "${positionOffset * 100} %position, 滑动的像素偏移：$positionOffsetPixels px")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.i(TAG, "滑动了一页 >>> position = $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.i(TAG, "滑动状态改变了:  state = $state")
            }
        })
    }

    private fun setupTabLayout() {
        // 将 ViewPager2 和 TabLayout 关联
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when(position) {
                // 调用 Tab的 setText() 设置 TabItem 的标题
                1 -> tab.text = "热门"
                2 -> tab.text = "影视"
                3 -> tab.text = "切换"
                else -> tab.text = "直播"
            }
        }.attach()
    }

}