# ViewBinding（视图绑定）
* Android Studio 3.6 新特性

* 【官方文档】 https://developer.android.google.cn/topic/libraries/view-binding?hl=zh-cn

* 【作用】 用于代替 findViewById（Kotlin 的扩展插件本质上也是 findViewById）

* 【使用方法】

  * Gradle 配置

    * ```groovy
      //app/build.gradle
      android {
          viewBinding {
              enabled true
          }
      }
      ```

  * 同步 Gradle 

  * 使用

    * ```java
      // 获取 ViewBinding 对象
      mBinding = ActivityJavaMainBinding.inflate(getLayoutInflater());
      setContentView(mBinding.getRoot()); // 替换原来的 R.layout.xml
      	// 访问控件
      mBinding.changeText.setOnClickListener((view) -> {
      	mBinding.textView.setText("你好，世界！");
      });
      ```

  * 【代码】
    * [Kotlin 版](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/MainActivity.kt)
    * [Java 版](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/JavaMainActivity.java)
    * [Fragment 中使用](https://github.com/ovomiao/Learning/tree/main/CS/Android/viewbinding/src/main/java/moe/maonaing/viewbinding/ViewBindingFragment.kt)


* 【Kotlin 扩展的缺点】
    * 容易引入错误的控件。
        * 例如：在 AActivity 中导入 BActivity 的控件，导致空指针异常
    * 效率低下
