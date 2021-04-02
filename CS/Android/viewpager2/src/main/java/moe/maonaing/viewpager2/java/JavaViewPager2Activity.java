package moe.maonaing.viewpager2.java;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import moe.maonaing.viewpager2.ChangeFragment;
import moe.maonaing.viewpager2.HotFragment;
import moe.maonaing.viewpager2.LiveFragment;
import moe.maonaing.viewpager2.MovieFragment;
import moe.maonaing.viewpager2.R;

/**
 * Java 版
 *  ViewPager2 + TabLayout
 */
public class JavaViewPager2Activity extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager2 = findViewById(R.id.viewPager2);


        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {

            @Override
            public int getItemCount() {
                return 4;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                Fragment fragment = new LiveFragment();
                switch (position) {
                    case 1:
                        fragment = new HotFragment();
                        break;
                    case 2:
                        fragment = new MovieFragment();
                        break;
                    case 3:
                        fragment = ChangeFragment.newInstance();
                        break;
                }
                return fragment;
            }

        };

        // 为 ViewPager2 设置适配器
        mViewPager2.setAdapter(adapter);

        // 将 ViewPager2 和 TabLayout 关联起来
        final TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                mTabLayout,
                mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("直播");
                                break;
                            case 1:
                                tab.setText("热门");
                                break;
                            case 2:
                                tab.setText("影视");
                                break;
                            case 3:
                                tab.setText("切换");
                                break;
                        }
                    }
                });

        // 关联
        tabLayoutMediator.attach();
    }

    public static void launch(@NonNull Context context) {
        Intent intent = new Intent(context, JavaViewPager2Activity.class);
        context.startActivity(intent);
    }

}