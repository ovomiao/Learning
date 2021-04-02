# ViewPager2 + TabLayout

## ViewPager2 基于 RecyclerView 实现

* 监听 ViewPager2 的滑动
* public void registerOnPageChangeCallback(@NonNull OnPageChangeCallback callback)

## 将 ViewPager2 和 TabLayout 关联
* TabLayoutMediator
    ```java
    public TabLayoutMediator(
          @NonNull TabLayout tabLayout,
          @NonNull ViewPager2 viewPager,
          @NonNull TabConfigurationStrategy tabConfigurationStrategy)

      public interface TabConfigurationStrategy {
        void onConfigureTab(@NonNull TabLayout.Tab tab, int position);
      }
      // 通过 tab.setText() 即可添加 Tab
    ```

```kotlin
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when(position) {
                1 -> tab.text = "热门"
                2 -> tab.text = "影视"
                else -> tab.text = "直播"
            }
        }.attach()
```