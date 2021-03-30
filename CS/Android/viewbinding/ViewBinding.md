# ViewBinding（视图绑定）
* Android Studio 3.6 新特性

* 【官方文档】 https://developer.android.google.cn/topic/libraries/view-binding?hl=zh-cn
*【作用】 用于代替 findViewById（Kotlin 的扩展插件本质上也是 findViewById）

*【使用方法】

    * 1. gradle 配置
 ```groovy
android {
                viewBinding {
                    enabled true
                }
        }
 ```

​        
​    * 2. 同步 Gradle 自动生成 ViewBinding 对象，生成 ViewBinding 类名为
​        布局文件名+Binding
​    * 3. 使用
​        * ```java
​          mBinding = ActivityJavaMainBinding.inflate(getLayoutInflater());
​          setContentView(mBinding.getRoot()); // 替换原来的 R.layout.xml

            // 访问控件
                  mBinding.changeText.setOnClickListener((view) -> {
                      mBinding.textView.setText("你好，世界！");
                  });
    
        ```
  * 【参考代码】
    * [Kotlin 版](/src/main/java/moe/maoniang/viewbinding/MainActivity.kt)
    * [Java 版](/src/main/java/moe/maoniang/viewbinding/JavaMainActivity.java)
